package fplibrary

trait Applicative[C[_]] extends Functor[C] {
  def pure[A](a: => A): C[A]
}

object Applicative extends Summoner[Applicative]
