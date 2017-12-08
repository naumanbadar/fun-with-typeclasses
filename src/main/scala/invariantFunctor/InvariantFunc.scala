package invariantFunctor

trait Codec[A] {
  outer =>
  def encode(v: A): String
  def decode(s: String): A
  def imap[B](dec: A => B, enc: B => A): Codec[B] = new Codec[B] {
    override def encode(v: B): String = outer.encode(enc(v))
    override def decode(s: String): B = dec(outer.decode(s))
  }
}

final case class Box[A](value: A)

object instances {
  implicit val stringCodec = new Codec[String] {
    override def encode(v: String): String = v
    override def decode(s: String): String = s
  }
  implicit val intCodec: Codec[Int] =
    stringCodec.imap(_.toInt, _.toString)
  implicit val booleanCodec: Codec[Boolean] =
    stringCodec.imap(_.toBoolean, _.toString)

  implicit def boxCodex[A](implicit c: Codec[A]): Codec[Box[A]] =
    c.imap[Box[A]](Box(_), _.value)
}

object syntax {
  implicit class encOps[A](v: A) {
    def encode(implicit c: Codec[A]) = c.encode(v)
  }

  implicit class decOps[A](s: String) {
    def decode(implicit c: Codec[A]) = c.decode(s)
  }

  def encode[A](value: A)(implicit c: Codec[A])  = c.encode(value)
  def decode[A](s: String)(implicit c: Codec[A]) = c.decode(s)

}

object InvariantFunc extends App {

  println("hello invar func")

  import instances._
  import syntax._

  println(true.encode)

  println("true".decode(booleanCodec))

  println(313134.encode)

  println("123412".decode(intCodec))

  println(Box(1234).encode)
  println(Box("Hello from Box").encode)

  println("123412".decode(boxCodex(intCodec)))

  println(decode[Box[String]]("box me"))

  println(encode(123123))

}
