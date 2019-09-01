import fplibrary._

import scala.io.StdIn

object PointProgram {
  def createIO(args: Array[String]): IO[Unit] =
    for {
      _ <- display(hyphens)
      _ <- display(question)
      input <- prompt
      integerAmount = convertStringToInt(input)
      positiveAmount = ensureAmountIsPositive(integerAmount)
      balance = round(positiveAmount)
      message = createMessage(balance)
      _ <- display(message)
      _ <- display(hyphens)
    } yield ()

  private val hyphens: String = "─" * 50
  private val question: String = "How much money would you like to deposit?"

  private def display(input: Any): IO[Unit] = IO.create(println(input))

  private def prompt: IO[String] = IO.create(StdIn.readLine())

  private def convertStringToInt(input: String): Int = input.toInt

  private def ensureAmountIsPositive(amount: Int): Int =
    if (amount < 1) 1
    else amount

  @scala.annotation.tailrec
  private def round(amount: Int): Int =
    if (isDivisibleByHundred(amount)) amount
    else round(amount + 1)

  private def isDivisibleByHundred(amount: Int): Boolean = amount % 100 == 0

  private def createMessage(balance: Int): String =
    s"Congratulations! You now have a balance of $$$balance."
}
