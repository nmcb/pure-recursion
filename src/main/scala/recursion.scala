object recursion {
  import scala.annotation.tailrec
  import utils._

  def odd[T](xs: Seq[T]): TailSafe[Boolean] =
    if (xs.isEmpty) done(false) else call(even(xs.tail))

  def even[T](xs: Seq[T]): TailSafe[Boolean] =
    if (xs.isEmpty) done(true) else call(odd(xs.tail))

  def ackermann(m: Int, n: Int): TailSafe[Int] = (m, n) match {
    case (0, _)                   => done(n + 1)
    case (_, 0) if m > 0          => call(ackermann(m - 1, 1))
    case (_, _) if m > 0 && n > 0 => for {
      inner <- call(ackermann(m, n -1))
      outer <- call(ackermann(m - 1, inner))
    } yield outer
  }
}
































// def ackermann(m: Int, n: Int): TailSafe[Int] = (m, n) match {
//   case (0,_)                   => done(n + 1)
//   case (_,0) if m > 0          => call(ackermann(m - 1, 1))
//   case (_,_) if m > 0 && n > 0 => for {
//     inner <- call(ackermann(m, n - 1))
//     outer <- call(ackermann(m - 1, inner))
//   } yield outer
// }
//
// def odd[T](xs: Seq[T]): TailSafe[Boolean] =
//   if (xs.isEmpty) done(false) else call(even(xs.tail))
//
// def even[T](xs: Seq[T]): TailSafe[Boolean] =
//   if (xs.isEmpty) done(true) else call(odd(xs.tail))
