package recursion

import org.scalatest.FunSpec
import org.scalatest.Assertion
import org.scalatest.Matchers._

class RecusionSpecification extends FunSpec {
  import client._

  describe("even/odd") {
    it("will return whether the passed seq is of even or odd length") {
      odd(Seq.empty).result   should be (false)
      even(Seq.empty).result  should be (true)

      odd(Seq(1)).result      should be (true)
      odd(Seq(1,2)).result    should be (false)
      odd(Seq(1,2,3)).result  should be (true)

      even(Seq(1)).result     should be (false)
      even(Seq(1,2)).result   should be (true)
      even(Seq(1,2,3)).result should be (false)
    }
    it("will not have side effects, as in blow up the stack") {
      odd(1 until 1000000).result  should be (true)
      even(1 to 1000000).result    should be (true)
    }
  }

  describe("ackermann") {
    it("will return right results for typical argument values") {
      ackermann(0,0).result should be (1)
      ackermann(1,1).result should be (3)
      ackermann(2,1).result should be (5)
      ackermann(1,2).result should be (4)

      ackermann(3,4).result should be (125)
    }
    it("will not have side effects, as in blow up the stack") {
      ackermann(3,12).result should be (32765)
    }
  }
}
