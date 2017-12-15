package monads

import cats.data.Writer
import cats.instances.vector._
import cats.syntax.writer._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object writer extends App {
  println("hello writer")

  def slowly[A](body: => A) =
    try body
    finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  //same as above but with Writer monad so the logs don't mix up in case of
  //overlapping of threads
  def factorialWriter(n: Int): Writer[Vector[String], Int] = {
    slowly(
      if (n == 0) 1.writer(Vector(s"fact 0 1"))
      else
        factorialWriter(n - 1).flatMap(v =>
          (n * v).writer(Vector(s"fact $n ${n * v}")))
    )
  }

  Await.result(
    Future.sequence(Vector(Future(factorial(5)), Future(factorial(4)))),
    5.seconds)

  println(
    Await.result(
      Future.sequence(
        Vector(Future(factorialWriter(5)), Future(factorialWriter(4)))),
      5.seconds))

  Future(5).flatMap(x => Future(10).map(y => x + y))

}
