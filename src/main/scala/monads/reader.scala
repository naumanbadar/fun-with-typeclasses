package monads

object reader extends App {

  import cats.data.Reader
  import cats.syntax.applicative._

  case class Db(
      usernames: Map[Int, String],
      passwords: Map[String, String]
  )

  type DbReader[B] = Reader[Db, B]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(db => db.usernames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(db => db.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] = {

    /*with flatMap but without pure
    findUsername(userId).flatMap(opn =>
      opn.map(n => checkPassword(n, password)).getOrElse(Reader(_ => false)))
     */

    /* with flatMap
    findUsername(userId).flatMap(
      _.map(checkPassword(_, password))
        .getOrElse(false.pure[DbReader]))
     */

    //with for comprehension
    for {
      opn <- findUsername(userId)
      b <- opn
            .map(checkPassword(_, password))
            .getOrElse(false.pure[DbReader])
    } yield b

  }

  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )

  val passwords = Map(
    "dade"  -> "zerocool",
    "kate"  -> "acidburn",
    "margo" -> "secret"
  )

  val db = Db(users, passwords)

  println(checkLogin(1, "zerocool").run(db))

  println(checkLogin(4, "davinci").run(db))

}
