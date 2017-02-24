import scala.util.{Try, Success, Failure}

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class RecursionSpec extends FunSpec {
  import recursion._

  describe("odd(xs: Seq[T]): Boolean") {
    it("should not blow up the stack") {
      odd(1 to 1000000).result should be (false)
    }
  }
  describe("ackermann(m: Int, n: Int): Int") {
    it("ackermann(3, 4): 125") {
      ackermann(3, 4).result should be (125)
    }
    it("should not blow up the stack") {
      ackermann(3,12).result should be (32765)
    }
  }

}


























// describe("odd(xs: Seq[T]): Boolean") {
//   it("should not blow up the stack") {
//     odd(1 to 1000000) should be (false)
//   }
// }
//
// describe("ackermann(m: Int, n: Int): Int") {
//   it("should not blow up the stack") {
//     ackermann(3,12).result should be (32765)
//   }
// }
