package fplibrary

final case class IO[+A](unsafeRun: () => A) extends AnyVal

object IO {
  implicit val m: Monad[IO] = new Monad[IO] {
    final def map[A, B](a: IO[A])(f: A => B): IO[B] = create(f(a.unsafeRun()))
    final def pure[A](a: => A): IO[A] = create(a)
    final def flatMap[A, B](a: IO[A])(f: A => IO[B]): IO[B] = create(f(a.unsafeRun()).unsafeRun())
    final def flatten[A](a: IO[IO[A]]): IO[A] = create(a.unsafeRun().unsafeRun())
  }

  def create[A](a: => A): IO[A] = IO(() => a)
}
