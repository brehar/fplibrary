package fplibrary

final case class State[S, +A](run: S => (A, S)) extends AnyVal {
  def runA(s: S): A = run(s)._1
  def runS(s: S): S = run(s)._2
}

object State {
  implicit def m[S]: Monad[State[S, ?]] = new Monad[State[S, ?]] {
    final def map[A, B](a: State[S, A])(f: A => B): State[S, B] = State { s =>
      val (aa, s1) = a.run(s)
      f(aa) -> s1
    }

    final def pure[A](a: => A): State[S, A] = create(a)

    final def flatMap[A, B](a: State[S, A])(f: A => State[S, B]): State[S, B] = State { s =>
      val (aa, s1) = a.run(s)
      val (bb, s2) = f(aa).run(s1)
      bb -> s2
    }

    final def flatten[A](a: State[S, State[S, A]]): State[S, A] = State { s =>
      val (aa, s1) = a.run(s)
      val (a1, s2) = aa.run(s1)
      a1 -> s2
    }
  }

  def create[S, A](a: => A): State[S, A] = State(s => (a, s))
}
