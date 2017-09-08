package recursion

object utils {
  import scala.annotation.tailrec

  sealed trait Pure[A] {
    @tailrec final def result: A = this match {
      case Done(a) => a
      case Call(t) => t().result
      case Cont(p, c) => p match {
        case Done(a)      => c(a).result
        case Call(t)      => t().flatMap(c).result
        case Cont(pp, cc) => pp.flatMap(a => cc(a).flatMap(c)).result
      }
    }

    // fmap :: (a -> b) -> (p a -> p b)
    def map[B](f: A => B): Pure[B] =
      flatMap(a => Done(f(a)))

    // (>>=) :: p a -> (a -> p b) -> p b
    def flatMap[B](f: A => Pure[B]): Pure[B] = this match {
      case Done(a)       => Call(() => f(a))
      case c: Call[A]    => Cont(c, f)
      case c: Cont[a1,_] => Cont(c.p, (a: a1) => c.c(a).flatMap(f))
    }
  }
  private case class Done[A](a: A)                          extends Pure[A]
  private case class Call[A](t: () => Pure[A])              extends Pure[A]
  private case class Cont[A,B](p: Pure[A], c: A => Pure[B]) extends Pure[B]

  def done[A](a: A): Pure[A]          = Done(a)
  def call[A](t: => Pure[A]): Pure[A] = Call(() => t)

  object Pure {
    implicit class DoneInt(i: Int) {
      def done: Pure[Int] = Done(i)
    }
    implicit class PureCall[A](p: => Pure[A]) {
      def call: Pure[A] = Call(() => p)
    }
  }
}
