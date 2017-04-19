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

  describe("maccarthy91") {
    it("will return right results for typical argument values") {
      mccarthy91(1000).result should be (990)
      mccarthy91(999).result  should be (989)


      mccarthy91(100).result should be (91)
      mccarthy91(99).result  should be (91)
      mccarthy91(98).result  should be (91)
      mccarthy91(97).result  should be (91)

      mccarthy91(10).result  should be (91)
      mccarthy91(2).result   should be (91)
      mccarthy91(1).result   should be (91)
      mccarthy91(0).result   should be (91)
    }
    it("will not have side effects, as in blow up the stack") {
      mccarthy91(1000000).result should be (999990)
    }
  }
}
