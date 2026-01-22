package playground

object SessionTypes:

  import scala.language.implicitConversions

  type Receiver = String
  type Address  = String
  type Content  = String

  case class Parcel[+S <: State](content: Content, receiver: Receiver, address: Address)

  sealed trait State
  case class Packaged   (byWhom:      String) extends State
  case class Send       (whoAccepts:  String) extends State
  case class Received   (whoDelivers: String) extends State
  case class Delivered  (toWhom:      String) extends State
  case class Lost       (witness:     String) extends State
  case class Moving     (tracking:    Long  ) extends State
  case class Destroyed  (when:        Long  ) extends State

  @annotation.implicitNotFound(msg = "state transition from ${A} to ${B} is not allowed")
  sealed abstract class ~>[-A,+B]

  implicit case object GoToThePost        extends ~>[Packaged,Send]
  implicit case object AcceptFromSender   extends ~>[Send,Moving]
  implicit case object BringToTheAddress  extends ~>[Moving,Received]
  implicit case object GiveToReceiver     extends ~>[Received,Delivered]
  implicit case object HaveFun            extends ~>[Moving,Destroyed]
  implicit case object DrinkAlcohol       extends ~>[Moving,Lost]
  implicit case object StopDrinking       extends ~>[Lost,Moving]

  trait Session[S]:
    type Self = S
    type Dual
    type DualOf[D] = Session[Self] { type Dual = D }
    def run (self: Self, dual: Dual): Unit

  def runSession[AS, D : Session[AS]#DualOf](session: AS, dual: D): Unit =
    implicitly[Session[AS]#DualOf[D]].run(session, dual)

  case class Stop(msg: String)
  case class In [ R[S <: State], A <: State, B <: State, +C](
    recv: R[A] => (C,R[B])
  )(
    implicit stateTransition: ~>[A,B]
  )
  case class Out[+R[S <: State], A <: State, +C](data: R[A], cont: C)

  implicit object StopDual extends Session[Stop]:
    type Dual = Stop
    def run (self: Self, dual: Dual): Unit = {}

  implicit def InDual[R[S<:State],RA<:State,RB<:State,C](implicit cont: Session[C]): Session[In[R, RA, RB, C]] {type Dual = Out[R, RA, cont.Dual]} =
    new Session[In[R,RA,RB,C]]:
      type Dual = Out[R,RA,cont.Dual]
      def run(self: Self, dual: Dual): Unit = cont.run(self.recv(dual.data)._1, dual.cont)

  implicit def OutDual[R[S<:State],RA<:State,RB<:State,C](implicit cont: Session[C]): Session[Out[R, RA, C]] {type Dual = In[R, RA, RB, cont.Dual]} =
    new Session[Out[R,RA,C]]:
      type Dual = In[R,RA,RB,cont.Dual]
      def run(self: Self, dual: Dual): Unit = cont.run(self.cont, dual.recv(self.data)._1)

  private def trivialServer = Stop("Done")
  private def trivialClient = Stop("Me too")
  def trivial(): Unit = runSession(trivialServer, trivialClient)

  private def server =
    In: (p: Parcel[Packaged]) =>
      val content = "Changed by server " + p.content
      val result = Parcel[Send](content, p.receiver, p.address)
      val stop = Stop("With server result: " + result)
      (stop, result)

  private def client =
    val parcel = Parcel[Packaged]("Content", "Receiver", "Address")
    val finish = Stop("With client result: " + parcel)
    Out(parcel, finish)

  def doServer() =
    In: (p: Parcel[Packaged]) =>
      val result = Parcel[Send]("Changed by server " + p.content, p.receiver, p.address)
      (
        In: (r: Parcel[Packaged]) =>
          println("Server result: " + result)
          val stop = Stop("With server result: " + result)
          val wrongOut = Out(result, stop)
          (stop, result)
        ,
          result
      )

  private def doClient() =
    val first = Parcel[Packaged]("First", "Receiver", "Address")
    val second = Parcel[Packaged]("Second", "Receiver", "Address")
    def finish =
      println("Client results: " + first + " and " + second)
      Stop("With client result: " + first)
    Out(first, Out(second, finish))

  def run(): Unit = runSession(server, client)

  def doRun(): Unit = runSession(doServer(), doClient())
