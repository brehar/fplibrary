import fplibrary.IO

object Interpreter {
  def main(args: Array[String]): Unit = {
    print(Console.RED)

    val io: IO[Unit] = PointProgram.createIO(args)

    def interpret[A](io: IO[A]): A = io.unsafeRun()

    print(Console.GREEN)
    interpret(io)
    print(Console.RESET)
  }
}
