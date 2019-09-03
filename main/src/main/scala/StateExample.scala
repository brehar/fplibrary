import fplibrary.State

object StateExample {
  def main(args: Array[String]): Unit = {
    println("–" * 50)

    val stateless: Int => String = i => s"$i is ${if (i % 2 == 0) "even" else "odd"}"
    val stateful: Int => (String, Int) = i => stateless(i) -> (i + 1)
    val state: State[Int, String] = State(stateful)

    val t1 @ (_, s1) = stateful(0)
    val t2 @ (_, s2) = stateful(s1)
    val t3 @ (_, s3) = stateful(s2)

    println(t1)
    println(t2)
    println(t3)

    println("–" * 25)

    println(s1)
    println(s2)
    println(s3)

    println("–" * 25)

    val newState: State[Int, List[String]] = for {
      a <- state
      b <- state
      c <- state
    } yield List(a, b, c)

    println(newState.run(0))

    println("–" * 50)
  }
}
