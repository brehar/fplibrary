package fplibrary

sealed abstract class Maybe[+A] extends Product with Serializable {
  final def getOrElse[S >: A](alternative: => S): S = this match {
    case Maybe.Just(a) => a
    case Maybe.Nothing => alternative
  }

  final def mapOrElse[B](alternative: => B)(f: A => B): B = this match {
    case Maybe.Just(a) => f(a)
    case Maybe.Nothing => alternative
  }

  final def isJust: Boolean = isInstanceOf[Maybe.Just[_]]

  final def isNothing: Boolean = !isJust
}

object Maybe {
  final case class Just[+A](a: A) extends Maybe[A]
  case object Nothing extends Maybe[Nothing]

  implicit val m: Monad[Maybe] = new Monad[Maybe] {
    def map[A, B](a: Maybe[A])(f: A => B): Maybe[B] = a match {
      case Just(a) => pure(f(a))
      case Nothing => Nothing
    }

    def pure[A](a: => A): Maybe[A] = Just(a)

    def flatMap[A, B](a: Maybe[A])(f: A => Maybe[B]): Maybe[B] = a match {
      case Just(a) => f(a)
      case Nothing => Nothing
    }

    def flatten[A](a: Maybe[Maybe[A]]): Maybe[A] = a match {
      case Just(a) => a
      case Nothing => Nothing
    }
  }
}
