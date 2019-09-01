package fplibrary

object PointFree {
  def compose[A, B, C](f: A => B, g: B => C): A => C = a => g(f(a))

  def composeKleisli[A, B, C, D[_]: FlatMap](f: A => D[B], g: B => D[C]): A => D[C] =
    a => FlatMap[D].flatMap(f(a))(g)
}
