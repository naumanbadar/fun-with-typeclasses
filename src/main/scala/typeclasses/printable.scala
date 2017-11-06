package typeclasses

trait Printable[A] {
  def format(a: A): String
}

object PrintableInstances {
  implicit val printableString: Printable[String] = identity[String]
  implicit val printableInt: Printable[Int] = a => a.toString
  implicit val printableCat: Printable[Cat] = a => s"${a.name} is a ${a.age} year-old ${a.color} cat."
}

object Printable {
  def format[A: Printable](a: A): String = implicitly[Printable[A]].format(a)
  def print[A: Printable](a: A): Unit = println(format(a))
}

final case class Cat(
                      name: String,
                      age: Int,
                      color: String
                    )

object PrintableSyntax {

  implicit class PrintOps[A](a: A) {
    def format(implicit p: Printable[A]): String = p.format(a)
    def print(implicit p: Printable[A]): Unit = println(format)
  }

}


object PrintableMain extends App {

  import PrintableInstances._

  Printable.print(1)
  Printable.print("hello 1")
  Printable.print(Cat("tom", 5, "white"))


  import PrintableSyntax._
  Cat("tom", 5, "white").print

}