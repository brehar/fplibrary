package object fplibrary {
  final implicit class InfixNotationForPointFree[A, B](private val f: A => B) extends AnyVal {
    @inline def -->[C](g: B => C): A => C = PointFree.compose(f, g)
  }

  final implicit class InfixNotationForPointFreeKleisli[A, B, D[_]](private val f: A => D[B])
      extends AnyVal {
    @inline def >=>[C](g: B => D[C])(implicit m: Monad[D]): A => D[C] =
      PointFree.composeKleisli(f, g)
  }
}
