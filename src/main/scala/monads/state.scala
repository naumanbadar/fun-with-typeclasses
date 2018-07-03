package monads

object state extends App {

  println("hello state")

  import cats.data.State

  val a = State[Int, String] { state =>
    (state, s"The state is $state")
  }

  println(a.run(5).value)
  println(a.runS(5).value)
  println(a.runA(5).value)

  private val demo: State[Int, Int] = State.get[Int]

  println(demo.run(9).value)

  type CalcState[A] = State[List[Int], A]

  def evalOne(sym: String): CalcState[Int] = {
    def operator(f: (Int, Int) => Int): State[List[Int], Int] =
      State[List[Int], Int] { st =>
        val res = f(st.head, st.tail.head)
        (res :: st.tail.tail, res)
      }

    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "/" => operator(_ / _)
      case "*" => operator(_ * _)
      case x =>
        State[List[Int], Int](st => {
          val int = x.toInt
          (int :: st, int)
        })
    }
  }

  println(evalOne("14").runA(Nil).value)

  val program = for {
    _   <- evalOne("1")
    _   <- evalOne("2")
    ans <- evalOne("+")
  } yield ans

  println(program.runA(Nil).value)

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(State.pure[List[Int], Int](0)) { (s, elm) =>
      s.flatMap(_ => evalOne(elm))
    }

  println(evalAll(List("1", "2", "+", "3", "*")).runA(Nil).value)

}
