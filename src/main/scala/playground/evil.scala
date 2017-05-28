package  playground

class Evil[A] {
  private val encapsulated = new java.util.ArrayList[A]

  def add(a: A): Unit = {
    encapsulated.add(a)
    ()
  }

  def addAll(as: List[A]): Unit = {
    as.foreach(encapsulated.add(_))
    // as.foreach(add(_))
    ()
  }

  def elements(): List[A] = {
    import scala.collection.JavaConverters._
    encapsulated.asScala.toList
  }
}

// Somewhere far away in another codebase ...

class Counted[A] extends Evil[A] {
  var count = 0

  override def add(a: A): Unit = {
    super.add(a)
    count = count + 1
  }

  override def addAll(as: List[A]): Unit = {
    super.addAll(as)
    count = count + as.length
  }
}
