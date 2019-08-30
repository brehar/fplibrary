package fplibrary

trait Monad[C[_]] {
  def flatMap[A, B](a: C[A])(f: A => C[B]): C[B]
}

object Monad {
  def apply[C[_]: Monad]: Monad[C] = implicitly[Monad[C]]
}
