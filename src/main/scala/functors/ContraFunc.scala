package functors

trait Printable[A] {
  def neoFormat(v: A): String

  def contraMap[B](f: B => A): Printable[B] = (v: B) => neoFormat(f(v))
}

final case class Box[A](value: A)

object instances {
  implicit val printableString = new Printable[String] {
    override def neoFormat(v: String): String = v.toUpperCase()
  }
  implicit val printableBoolean = new Printable[Boolean] {
    override def neoFormat(v: Boolean): String = if (v) "yes" else "no"
  }
  implicit def printableBox[A](implicit p: Printable[A]): Printable[Box[A]] = {
    p.contraMap[Box[A]](b => b.value)
  }
}

object syntax {
  implicit class PrintableSyntax[A](v: A) {
    def neoFormat(implicit p: Printable[A]) = {
      p.neoFormat(v)
    }
  }
}

object ContraFunc extends App {
  println("hello")

  import instances._
  import syntax._

  println("Hello from printable".neoFormat)

  println(true.neoFormat)
  println(false.neoFormat)

  println(Box("Hello box").neoFormat)
  println(Box(true).neoFormat)

}
