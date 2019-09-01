package fplibrary

trait Monad[C[_]] extends FlatMap[C] with Applicative[C] {
  def flatten[A](a: C[C[A]]): C[A]
}

object Monad extends Summoner[Monad]
