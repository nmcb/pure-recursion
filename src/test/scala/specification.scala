import org.scalatest.FunSpec
import org.scalatest.Matchers._

class RecursionSpec extends FunSpec {
  import recursion._

  describe("odd/even[A](xs:Seq[A]): Boolean") {
    it("is correct") {
      odd(Seq.empty).result     should be (false)
      odd(Seq(1)).result        should be (true)
      odd(Seq(1, 2)).result     should be (false)
      odd(Seq(1, 2, 3)).result  should be (true)

      even(Seq.empty).result    should be (true)
      even(Seq(1)).result       should be (false)
      even(Seq(1, 2)).result    should be (true)
      even(Seq(1, 2, 3)).result should be (false)
    }
    it("is pure, i.e. doesn't blow up the stack as a side efect") {
      odd(1 to 1000000).result  should be (false)
      even(1 to 1000000).result should be (true)
    }
  }
  describe("ackermann(m: Int, n: Int): Int") {
    it("is correct") {
      ackermann(0,0).result should be (1)
      ackermann(1,4).result should be (6)
      ackermann(3,3).result should be (61)
    }
    it("is pure, i.e. doesn't blow up the stack as a side efect") {
      ackermann(3, 12).result should be (32765)
    }
  }
}
