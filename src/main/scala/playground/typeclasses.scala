// package typeclasses
//
// object typeclasses {
//
//   trait Ranged[V] {
//     def range(v: V): Range
//   }
//
//   trait Keyed[K,V] {
//     def keys(v: V): Set[K]
//   }
//
//   class Cache[K,V](val underlying: Set[V])(implicit keyed: Keyed[K,V], ranged: Ranged[V]) {
//
//     def find(key: K): Set[V] =
//       underlying.filter(a => keyed.keys(a).contains(key))
//
//     def find(instant: Instant): Set[V] =
//       underlying.filter(a => bounded.interval(a).contains(instant))
//   }
//
//   object Cache {
//     def apply[K,V](values: Set[V])(implicit keyed: Keyed[K,V], bounded: IntervalBounded[V]): Cache[K,V] =
//       new Cache(values)(keyed, bounded)
//   }
// }
//
// object client extends App {
//
//   import typeclasses._
//
//   case class Data(id: Long, key: String, start: Instant, stop: Instant)
//
//   object Implicits {
//     implicit object IntervalBoundedData extends IntervalBounded[Data] {
//       override def interval(data: Data): Range =
//         Range(data.start, data.stop)
//     }
//     implicit object KeyedData extends Keyed[String,Data] {
//       override def keys(data: Data): Set[String] =
//         data.key.split("\\s+").toSet
//     }
//   }
//
//   val d1 = Data(0, "a b", 0, 5)
//   val d2 = Data(1, "b c", 0, 5)
//   val d3 = Data(2, "a c", 5, Int.MaxValue)
//   val fixture = Set(d1, d2, d3)
//
//   import Implicits._
//
//   val cache   = Cache(fixture)
//
//   assert(cache.find("a").equals(Set(d1, d3)), s"""cache.find("a") was: ${cache.find("a")}""")
//   assert(cache.find("b").equals(Set(d1, d2)), s"""cache.find("b") was: ${cache.find("b")}""")
//   assert(cache.find("c").equals(Set(d2, d3)), s"""cache.find("c") was: ${cache.find("c")}""")
//
//   assert(cache.find(4).equals(Set(d1, d2)), s"""cache.find(4) was: ${cache.find(4)}""")
//   assert(cache.find(5).equals(Set(d3)), s"""cache.find(5) was: ${cache.find(5)}""")
//   }
// }
