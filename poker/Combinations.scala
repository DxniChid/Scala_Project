package poker

object Combinations:

  def choose[A](items: List[A], amount: Int): List[List[A]] =
    if amount == 0 then
      List(Nil)
    else if items.isEmpty then
      Nil
    else
      val head = items.head
      val tail = items.tail

      val withHead =
        choose(tail, amount - 1)
          .map(combination => head :: combination)

      val withoutHead =
        choose(tail, amount)

      withHead ++ withoutHead