import scala.io.StdIn

object OriginalProgram {
  def run(args: Array[String]): Unit = {
    display(hyphens)
    display(question)

    val input: String = prompt()
    val integerAmount: Int = convertStringToInt(input)
    val positiveAmount: Int = ensureAmountIsPositive(integerAmount)
    val balance: Int = round(positiveAmount)
    val message: String = createMessage(balance)

    display(message)
    display(hyphens)
  }

  private val hyphens: String = "─" * 50
  private val question: String = "How much money would you like to deposit?"

  private def display(input: Any): Unit = println(input)

  private def prompt(): String = StdIn.readLine()

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
