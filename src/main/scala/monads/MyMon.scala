package monads
import scala.language.higherKinds

trait Monad[F[_]] {

  def pure[A](value: A): F[A]

  def flatMap[A, B](value: F[A])(f: A => F[B]): F[B]

  def map[A, B](value: F[A])(f: A => B): F[B] = flatMap(value)(a => pure(f(a)))
}

object MyMonad extends App {

  println(
    Option(1)
      .flatMap(i => Option(i + 1))
      .flatMap(y => Option(y + 1))
      .map(_ * 2))

}
