import fplibrary._

import scala.io.StdIn

object PointProgram {
  def createIO(args: Array[String]): IO[Unit] = loop(2)

  def loop(triesLeftAfterThisIteration: Int): IO[Unit] =
    for {
      _ <- display(hyphens)
      _ <- display(question)
      input <- prompt
      result <- fromInputToTuple(input)
      (message, wasInputValid) = result
      _ <- display(message)
      _ <- display(hyphens)
      _ <- decideWhatToDoNext(wasInputValid, triesLeftAfterThisIteration)
    } yield ()

  private lazy val stateful: Int => (IO[Unit], Int) = tries => loop(tries) -> (tries - 1)
  private lazy val state: State[Int, IO[Unit]] = State(stateful)
  private lazy val ranOutOfTries: IO[Unit] =
    for {
      _ <- display("You ran out of tries.")
      _ <- display(hyphens)
    } yield ()

  private val hyphens: String = "─" * 50
  private val question: String = "How much money would you like to deposit?"

  private def decideWhatToDoNext(
      wasInputValid: Boolean,
      triesLeftAfterThisIteration: Int
  ): IO[Unit] =
    if (wasInputValid) IO.create(())
    else if (triesLeftAfterThisIteration == 0) ranOutOfTries
    else tryAgain(triesLeftAfterThisIteration)

  private def tryAgain(triesLeftAfterThisIteration: Int): IO[Unit] =
    state.flatMap(_ => state).runA(triesLeftAfterThisIteration)

  private def fromInputToTuple(input: String): IO[(String, Boolean)] = IO.create {
    fromInputToMessage(input) -> convertStringToInt(input).isJust
  }

  private def fromInputToMessage(input: String): String =
    convertStringToInt(input).mapOrElse("Error! Not a valid number.")(fromIntegerInputToMessage)

  private def fromIntegerInputToMessage(integerAmount: Int): String =
    createMessage(round(ensureAmountIsPositive(integerAmount)))

  private def display(input: Any): IO[Unit] = IO.create(println(input))

  private def prompt: IO[String] = IO.create(StdIn.readLine())

  private def convertStringToInt(input: String): Maybe[Int] =
    try Maybe.Just(input.toInt)
    catch {
      case _: NumberFormatException => Maybe.Nothing
    }

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
