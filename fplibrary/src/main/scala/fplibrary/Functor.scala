package fplibrary

trait Functor[C[_]] {
  def map[A, B](a: C[A])(f: A => B): C[B]
}

object Functor extends Summoner[Functor]
