object utils {
  import scala.annotation.tailrec

  sealed trait TailSafe[A] {
    @tailrec final def result: A = this match {
      case Done(a)     => a
      case Call(t)     => t().result
      case Cont(ts, c) => ts match {
        case Done(a)       => c(a).result
        case Call(t)       => t().flatMap(c).result
        case Cont(cts, cc) => cts.flatMap(a => cc(a).flatMap(c)).result
      }
    }

    def flatMap[B](f: A => TailSafe[B]): TailSafe[B] = this match {
      case Cont(ts, c)    => Cont(ts, (x: Any) => c(x).flatMap(f))
      case t: TailSafe[A] => Cont(t, f)
    }

    def map[B](f: A => B): TailSafe[B] = flatMap(a => Done(f(a)))
  }

  private case class Done[A](a: A) extends TailSafe[A]
  private case class Call[A](t: () => TailSafe[A]) extends TailSafe[A]
  private case class Cont[A,B](ts: TailSafe[A], c: A => TailSafe[B]) extends TailSafe[B]

  def done[A](a: A): TailSafe[A] = Done(a)
  def call[A](t: => TailSafe[A]): TailSafe[A] = Call(() => t)
}









































// sealed trait TailSafe[A] {
//   @tailrec final def result: A = this match {
//     case Done(a)     => a
//     case Call(t)     => t().result
//     case Cont(ts, c) => ts match {
//       case Done(a)       => c(a).result
//       case Call(t)       => t().flatMap(c).result
//       case Cont(cts, cc) => cts.flatMap(a => cc(a).flatMap(c)).result
//     }
//   }
//
//   def flatMap[B](f: A => TailSafe[B]): TailSafe[B] = this match {
//     case Cont(ts, c)    => Cont(ts, (x: Any) => c(x).flatMap(f))
//     case t: TailSafe[A] => Cont(t, f)
//   }
//
//   def map[B](f: A => B): TailSafe[B] = flatMap(a => Done(f(a)))
// }
//
// private case class Done[A](a: A) extends TailSafe[A]
// private case class Call[A](t: () => TailSafe[A]) extends TailSafe[A]
// private case class Cont[A,B](ts: TailSafe[A], f: A => TailSafe[B]) extends TailSafe[B]
//
// def done[A](a: A): TailSafe[A] = Done(a)
// def call[A](t: => TailSafe[A]): TailSafe[A] = Call(() => t)
