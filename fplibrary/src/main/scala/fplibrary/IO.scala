package fplibrary

final case class IO[+A](unsafeRun: () => A) extends AnyVal

object IO {
  implicit val m: Monad[IO] = new Monad[IO] {
    final def flatMap[A, B](a: IO[A])(f: A => IO[B]): IO[B] = create(f(a.unsafeRun()).unsafeRun())
  }

  def create[A](a: => A): IO[A] = IO(() => a)
}
