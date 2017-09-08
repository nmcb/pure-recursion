package recursion

import org.scalatest.FunSpec
import org.scalatest.Assertion
import org.scalatest.Matchers._

class SugarTests extends FunSpec {
  import utils._
  import utils.Pure._

  def sugaredMcCarthy91(n: Int): Pure[Int] =
    if (n > 100)
      (n - 10).done
    else for {
      inner <- sugaredMcCarthy91(n + 11)
      outer <- sugaredMcCarthy91(inner)
    } yield outer

  describe("sugaredMcCarthy91") {
    it("will return right results for typical argument values") {
      sugaredMcCarthy91(1000).result should be (990)
      sugaredMcCarthy91(999).result  should be (989)

      sugaredMcCarthy91(100).result should be (91)
      sugaredMcCarthy91(99).result  should be (91)
      sugaredMcCarthy91(98).result  should be (91)
      sugaredMcCarthy91(97).result  should be (91)

      sugaredMcCarthy91(10).result  should be (91)
      sugaredMcCarthy91(2).result   should be (91)
      sugaredMcCarthy91(1).result   should be (91)
      sugaredMcCarthy91(0).result   should be (91)
    }
    it("will not have side effects, as in blow up the stack") {
      sugaredMcCarthy91(1000000).result should be (999990)
    }
  }

  def sugaredAckermann(m: Int, n: Int): Pure[Int] = (m,n) match {
    case (0,_)                   => (n + 1).done
    case (_,0) if m > 0          => sugaredAckermann(m - 1, 1)
    case (_,_) if m > 0 && n > 0 => for {
      inner <- sugaredAckermann(m , n - 1)
      outer <- sugaredAckermann(m - 1, inner)
    } yield outer
  }

  describe("sugaredAckermann") {
    it("will return right results for typical argument values") {
      sugaredAckermann(0,0).result should be (1)
      sugaredAckermann(1,1).result should be (3)
      sugaredAckermann(2,1).result should be (5)
      sugaredAckermann(1,2).result should be (4)
      sugaredAckermann(3,4).result should be (125)
    }
  }
}
