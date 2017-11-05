package typeclasses

import org.scalatest.{FlatSpec, Matchers}

class Test extends FlatSpec with Matchers {

  private val person = Person("TomCat", "tc007")

  import TypeClassesInstances._

  behavior of "Typeclasses"

  it must "instantiate correct type" in {

    import JsonSyntax._
    person.toJson shouldBe JsObject(Map("name" -> JsString("TomCat"), "handle" -> JsString("tc007")))
  }

}
