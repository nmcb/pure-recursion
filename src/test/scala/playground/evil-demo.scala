// package playground
//
// package recursion
//
// import org.scalatest.FunSpec
// import org.scalatest.Assertion
// import org.scalatest.Matchers._
//
// class EvilSpec extends FunSpec {
//
//   describe("Evil") {
//     it("should add an element to the contained list of elements") {
//       val e = new Evil[Int]
//       e.elements should be(List.empty)
//       e.add(1)
//       e.elements should be(List(1))
//     }
//     it("should addAll elements to the contained list of elements") {
//       val e = new Evil[Int]
//       e.elements should be(List.empty)
//       e.addAll(List(1,2,3))
//       e.elements should be(List(1,2,3))
//     }
//   }
// }
//
// // Somewhere far away in another codebase ...
//
// class Countedpec extends FunSpec {
//
//   describe("Counted") {
//     it("should add an element to the counted list of elements") {
//       val c = new Counted[Int]
//       c.count should be(0)
//       c.add(1)
//       c.count should be(1)
//     }
//     it("should addAll elements to the counted list of elements") {
//       val c = new Counted[Int]
//       c.count should be(0)
//       c.addAll(List(1,2,3))
//       c.count should be(3)
//     }
//   }
// }
