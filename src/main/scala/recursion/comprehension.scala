// package recursion
//
// object comprehension {
//
//   import annotation.tailrec
//
//   sealed trait Pure[A] {
//     def step: Either[Pure[A], A] = this match {
//       case Done(a)       => Right(a)
//       case x: NonTerm[A] => Left(x match {
//         case Call(t                      ) => t()
//         case Cont(Done(a            ), c ) => c(a)
//         case Cont(Call(t            ), c ) => Cont(t(), c)
//         case Cont(Cont(p: Pure[a], c), cc) => Cont(p, (pr: a) => Cont(c(pr), cc))
//       })
//     }
//
//     @tailrec final def compute(debug: Pure[A] => Unit = _ => ()): A = {
//       debug(this)
//       step match {
//         case Right(a) => a
//         case Left (p) => p.compute(debug)
//       }
//     }
//
//     def flatMap[B](f: A => Pure[B]): Pure[B] = comprehension.flatMap(this)(f)
//     def map    [B](f: A => B      ): Pure[B] = comprehension.map    (this)(f)
//   }
//
//   final private[this] case class Done[A](a: A) extends Pure[A]
//
//   sealed private[this] trait      NonTerm[A]                              extends Pure   [A]
//   final  private[this] case class Call[A   ](t: () => Pure[A]           ) extends NonTerm[A]
//   final  private[this] case class Cont[A, B](p: Pure[A], c: A => Pure[B]) extends NonTerm[B]
//
//   def done   [A   ](a: A                        ): Pure[A] = Done(a      )
//   def call   [A   ](p: => Pure[A]               ): Pure[A] = Call(() => p)
//   def flatMap[A, B](pa: Pure[A])(f: A => Pure[B]): Pure[B] = Cont(pa, f  )
//   def map    [A, B](pa: Pure[A])(f: A =>      B ): Pure[B] = flatMap(pa)(a => done(f(a)))
// }
//
// object Main {
//
//   import comprehension._
//
//   def ackermann(m: Int, n: Int): Pure[Int] = (m, n) match {
//     case (0, _) => done(n + 1)
//     case (m, 0) if m > 0 => call(ackermann(m - 1, 1))
//     case (m, n) if m > 0 && n > 0 =>
//       for {
//         inner <- call(ackermann(m, n - 1))
//         outer <- call(ackermann(m - 1, inner))
//       } yield outer
//   }
//
//   def ackermannI(m: Int, n: Int): Int = (m, n) match {
//     case (0, _) => n + 1
//     case (m, 0) if m > 0 => ackermannI(m - 1, 1)
//     case (m, n) if m > 0 && n > 0 => ackermannI(m - 1, ackermannI(m, n - 1))
//   }
//
//   def main(args: Array[String]): Unit = {
//     val logStridePower = 9
//     val logStride = math.pow(10, logStridePower).toLong
//     var contStep  = 0l
//
//     def debug[A](x: Pure[A]): Unit = {
//       if (contStep % logStride == 0) println(s"Step ${contStep / logStride}*10^$logStridePower")
//       contStep += 1
//     }
//
//     println(ackermann(4, 1).compute(debug))
//   }
// }
