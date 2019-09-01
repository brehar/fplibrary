package object fplibrary {
  final implicit class InfixNotationForPointFree[A, B](private val f: A => B) extends AnyVal {
    @inline def -->[C](g: B => C): A => C = PointFree.compose(f, g)
  }

  final implicit class InfixNotationForPointFreeKleisli[A, B, D[_]](private val f: A => D[B])
      extends AnyVal {
    @inline def >=>[C](g: B => D[C])(implicit m: Monad[D]): A => D[C] =
      PointFree.composeKleisli(f, g)
  }

  final implicit class InfixNotationForHigherKinds[A, C[_]](private val a: C[A]) extends AnyVal {
    @inline def map[B](f: A => B)(implicit functor: Functor[C]): C[B] = functor.map(a)(f)
    @inline def flatMap[B](f: A => C[B])(implicit fm: FlatMap[C]): C[B] = fm.flatMap(a)(f)
    @inline def flatten[B](implicit m: Monad[C], view: C[A] => C[C[B]]): C[B] = m.flatten(view(a))
  }
}
