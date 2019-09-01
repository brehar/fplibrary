package fplibrary

import org.scalatest.{ FunSuite, MustMatchers }

class MonadLawsTest extends FunSuite with MustMatchers {
  val f: Int => IO[String] = a => IO.create(a.toString)
  val g: String => IO[Unit] = a => IO.create(println(a))

  val v: Int = 5
  val io: IO[Int] = IO.create(v)

  test("left identity") {
    val lhs = io.flatMap(f)
    val rhs = f(v)

    lhs.unsafeRun() mustBe rhs.unsafeRun()
  }

  test("right identity") {
    val lhs = io.flatMap(IO.create(_))
    val rhs = io

    lhs.unsafeRun() mustBe rhs.unsafeRun()
  }

  test("associativity") {
    val lhs = io.flatMap(f).flatMap(g)
    val rhs = io.flatMap(x => f(x).flatMap(g))

    lhs.unsafeRun() mustBe rhs.unsafeRun()
  }
}
