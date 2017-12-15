package monads

object Identity extends App {

  type Id[A] = A

  implicit val idMonad = new Monad[Id] {

    override def pure[A](value: A): Id[A] = value

    override def flatMap[A, B](value: Id[A])(f: A => Id[B]): Id[B] = f(value)

    override def map[A, B](value: Id[A])(f: A => B): Id[B] = f(value)
  }

  println("Dave": Id[String])

  println(idMonad.pure(123))

  import idMonad._

  println(map(123)(_ + 1))

  println(flatMap(321)(_ + 1))
}
