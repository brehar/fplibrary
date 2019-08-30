package fplibrary

object PointFree {
  def compose[A, B, C](f: A => B, g: B => C): A => C = a => g(f(a))

  def composeKleisli[A, B, C, D[_]: Monad](f: A => D[B], g: B => D[C]): A => D[C] =
    a => Monad[D].flatMap(f(a))(g)
}
