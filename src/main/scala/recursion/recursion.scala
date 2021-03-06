package recursion

object client {
  import utils._

  // A JVM SOE reason, recursively calling to and fro multiple functions

  def odd[A](xs: Seq[A]): Pure[Boolean] =
    if (xs.isEmpty) done(false) else call(even(xs.tail))

  def even[A](xs: Seq[A]): Pure[Boolean] =
    if (xs.isEmpty) done(true) else call(odd(xs.tail))


  // Another JVM SOE reason, a nested recursive call

  def ackermann(m: Int, n: Int): Pure[Int] = (m,n) match {
    case (0,_)                   => done(n + 1)
    case (_,0) if m > 0          => call(ackermann(m - 1, 1))
    case (_,_) if m > 0 && n > 0 => for {
      inner <- call(ackermann(m , n - 1))
      outer <- call(ackermann(m - 1, inner))
    } yield outer
  }

  def mccarthy91(n: Int): Pure[Int] =
    if (n > 100)
      done(n - 10)
    else for {
      inner <- call(mccarthy91(n + 11))
      outer <- call(mccarthy91(inner))
    } yield outer
}
