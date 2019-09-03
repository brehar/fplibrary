import scala.concurrent.{ ExecutionContext, ExecutionContextExecutor, Future }

object FutureExample {
  def main(args: Array[String]): Unit = {
    implicit val ec: ExecutionContextExecutor = ExecutionContext.global

    lazy val f1: Future[Int] = Future {
      println(Console.GREEN + "first future started" + Console.RESET)
      Thread.sleep(2000)
      println(Console.GREEN + "first future finished" + Console.RESET)
      5
    }

    println("after first future")

    lazy val f2: Future[Int] = Future {
      println(Console.YELLOW + "second future started" + Console.RESET)
      Thread.sleep(1000)
      println(Console.YELLOW + "second future finished" + Console.RESET)
      10
    }

    println("after second future")

    val _: Future[Int] = f1.flatMap { a =>
      f2.map { b =>
        println(Console.RED + "third future started" + Console.RESET)
        val result: Int = a + b
        println(Console.RED + "third future finished" + Console.RESET)
        result
      }
    }

    println("after third future")
    Thread.sleep(4000)
    println("shutting down")
  }
}
