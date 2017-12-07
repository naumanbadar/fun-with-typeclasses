package monoids

import cats.Monoid
import cats.instances.string._
import cats.instances.option._
import cats.instances.int._
import cats.instances.double._
import cats.syntax.semigroup._

class SuperAdder() {
  def add(items: List[Int]): Int = {
//    Monoid[Int].combineAll(items)
    items.foldLeft(Monoid[Int].empty)(_ |+| _)
  }
  def addG[T](items: List[T])(implicit monoid: Monoid[T]) =
    items.foldLeft(monoid.empty)(_ |+| _)
}

case class Order(totalCost: Double, quantity: Double)

object oderMonoid {

  implicit val ordM = new Monoid[Order] {
    override def empty: Order = Order(0.0, 0.0)
    override def combine(x: Order, y: Order): Order = Order(
      Monoid[Double].combine(x.totalCost, y.totalCost),
      Monoid[Double].combine(x.quantity, y.quantity)
    )
  }
}

object Main extends App {
  println(
    Monoid[String].combine("a", "b")
  )
  println(Monoid[Option[Int]].combine(Option(2), Option(5)))
  println(5 |+| 6)

  println(new SuperAdder().addG(List(1, 2, 3)))

  println(new SuperAdder().addG(List(Some(1), None, Some(3))))

  val ord1 = Order(1.1,5.5)
  val ord2 = Order(1.1,5.5)
  import oderMonoid._

  println(new SuperAdder().addG(List(ord1, ord2)))
}
