package fplibrary

trait FlatMap[C[_]] extends Functor[C] {
  def flatMap[A, B](a: C[A])(f: A => C[B]): C[B]
}

object FlatMap extends Summoner[FlatMap]
