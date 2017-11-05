package typeclasses

case class Person(name: String, handle: String)


//type class
trait JsonWriter[A] {
  def write(value: A): Json
}

//type class instances
object TypeClassesInstances {

  implicit val stringWriter = new JsonWriter[String] {
    override def write(value: String) = JsString(value)
  }

  implicit val personWriter = new JsonWriter[Person] {
    override def write(value: Person): Json =
      JsObject(Map(
        "name" -> JsString(value.name),
        "handle" -> JsString(value.handle)
      ))
  }
}



//interface object with context bound
object JsonImp {
  def toJson[A: JsonWriter](value: A): Json = {
    implicitly[JsonWriter[A]].write(value)
  }
}


//interface syntax
object JsonSyntax {

  implicit class JsonWriterOps[B](value: B) {
    //    def toJson[B: JsonWriter](): Json = implicitly[JsonWriter[B]].write(value)

    def toJson(implicit w: JsonWriter[B]): Json = w.write(value)
  }

}


object Main extends App {

  import TypeClassesInstances._

  println("hello type classes")
  private val naumanPerson = Person("nauman", "nomi")

  //without interface
  println(personWriter.write(naumanPerson))

  //with oject interface
  println(Json.toJson(naumanPerson))

  //with oject interface but with new implicit syntax of context bound
  println(JsonImp.toJson(naumanPerson))


  import JsonSyntax._
  println(naumanPerson.toJson)

}