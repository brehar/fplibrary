package fplibrary

final case class Reader[-D, +A](run: D => A) extends AnyVal

object Reader {
  implicit def m[D]: Monad[Reader[D, ?]] = new Monad[Reader[D, ?]] {
    final def map[A, B](a: Reader[D, A])(f: A => B): Reader[D, B] = Reader(d => f(a.run(d)))

    final def pure[A](a: => A): Reader[D, A] = create(a)

    final def flatMap[A, B](a: Reader[D, A])(f: A => Reader[D, B]): Reader[D, B] =
      Reader(d => f(a.run(d)).run(d))

    final def flatten[A](a: Reader[D, Reader[D, A]]): Reader[D, A] = Reader(d => a.run(d).run(d))
  }

  def create[D, A](a: => A): Reader[D, A] = Reader(_ => a)
}
