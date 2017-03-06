object recursion {
  import utils._

  def odd[A](xs: Seq[A]): Pure[Boolean] =
    if (xs.isEmpty) done(false) else call(even(xs.tail))

  def even[A](xs: Seq[A]): Pure[Boolean] =
    if (xs.isEmpty) done(true) else call(odd(xs.tail))

  def ackermann(m: Int, n: Int): Pure[Int] = (m,n) match {
    case (0,_)                   => done(n + 1)
    case (_,0) if m > 0          => call(ackermann(m - 1, 1))
    case (_,_) if m > 0 && n > 0 => for {
      inner <- call(ackermann(m, n - 1))
      outer <- call(ackermann(m - 1, inner))
    } yield outer
  }
}
