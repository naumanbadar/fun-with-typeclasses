package functors

import cats.Functor
import cats.syntax.functor._

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A)                        extends Tree[A]

object Tree {
  def branch[A](l: Tree[A], r: Tree[A]): Tree[A] = Branch(l, r)
  def leaf[A](v: A): Tree[A]                     = Leaf(v)

  /* doesn't compile
  def branch[A](l: Tree[A],r:Tree[A])= Branch(l,r)
  def leaf[A](v : A) = Leaf(v)
 */

}

object ins extends App {

  implicit val treeFunctor = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
      case Leaf(value)         => Leaf(f(value))
    }
  }

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  println(treeFunctor.map(Branch(Leaf(10), Leaf(20)))(_ * 2))

//  Branch(Leaf(10),Leaf(5)).map(_ + 1)
  println(Tree.branch(Tree.leaf(10), Tree.leaf(5)).map(_ + 1))


  import cats.instances.function._
  val f1: Int => Int = x => x + 1
  val f2: Int => Int = x => x + 2
  val f3: Int => Int = x => x + 3

  val f4 = f1.map(f2).map(f3)

}
