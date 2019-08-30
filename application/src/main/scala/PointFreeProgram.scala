import fplibrary._

import scala.io.StdIn

object PointFreeProgram {
  lazy val createIO: Array[String] => IO[Unit] =
    ignoreArgs --> hyphens --> display >=> question --> display >=> prompt >=> convertStringToInt --> ensureAmountIsPositive --> round --> createMessage --> display >=> hyphens --> display

  private lazy val ignoreArgs: Array[String] => Unit = _ => ()

  private lazy val hyphens: Any => String = _ => "─" * 50

  private lazy val question: Any => String = _ => "How much money would you like to deposit?"

  private lazy val display: Any => IO[Unit] = input => IO.create(println(input))

  private lazy val prompt: Any => IO[String] = _ => IO.create(StdIn.readLine())

  private lazy val convertStringToInt: String => Int = _.toInt

  private lazy val ensureAmountIsPositive: Int => Int = amount =>
    if (amount < 1) 1
    else amount

  private lazy val round: Int => Int = amount =>
    if (isDivisibleByHundred(amount)) amount
    else round(amount + 1)

  private lazy val isDivisibleByHundred: Int => Boolean = _ % 100 == 0

  private lazy val createMessage: Int => String = balance =>
    s"Congratulations! You now have a balance of $$$balance."
}
