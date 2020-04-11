package differ.core


/**
  * This is similar to the scala "Option" abstract class but will be used for the "Diff" Models.
  * We can use this data type to deal with delta changes of any model
  *
  * This can have either a DiffSome(A) or a DiffNone value.
  *
  * @tparam A
  */
sealed abstract class DiffOption[+A] extends Product with Serializable {
  self =>

  /**
    * Returns true if the option is DiffNone, false otherwise. This implies that there are no
    * changes from the previous value.
    */
  def isEmpty: Boolean

  /**
    * Returns true if the option is an instance of DiffSome, false otherwise.
    * This implies that there is some change, or diff from the previous value.
    */
  def isDefined: Boolean = !isEmpty

  /**
    * Returns a DiffSome or a DiffHistory with the result containing the computations to the value based on the f
    * @param f The function to apply
    * @return
    */
  @inline def map[B](f: A => B): DiffOption[B]


  /**
    * Returns the DiffOption's value.
    *  @note The DiffOption must be nonEmpty.
    */
  @throws[java.util.NoSuchElementException]
  def get: A

  /**
    * Returns the option's value if the option is nonempty, otherwise
    * return the result of evaluating `default`.
    *
    *  @param default  the default expression.
    */
  @inline final def getOrElse[B >: A](default: => B): B =
    if (isEmpty) default else this.get

  /**
    * Returns a singleton list containing the option's value
    * if it is nonempty, or the empty list if the option is empty.
    */
  def toList: List[A] =
    if (isEmpty) Nil else new ::(this.get, Nil)

  /**
    * The |> operator simply fetches which is supposed to be the latest value from a couple of
    * DiffOption.
    *
    * eg, DiffSome(2) |> DiffSome(3) = DiffSome(3)
    *     DiffSome(2) |> DiffNone = DiffSome(2)
    *     DiffNone |> DiffNone = DiffNone
    *    DiffSome(2) |> DiffHistory(3,4) = DiffSome(3)
    *    DiffHistory(3,4) |> DiffSome(1) = DiffSome(1)
    *
    *
    * @return The latest value from the two DiffOption values.
    */
  def |>[B >: A](that: DiffOption[B]): DiffOption[B] =
    if (that.isDefined) that else this


  /**
    * The <| operator simply fetches which is supposed to be the oldest value from a couple of
    * DiffOption.
    *
    * eg, DiffSome(1) <| DiffSome(2) = DiffSome(1)
    *     DiffSome(1) <| DiffNone = DiffSome(1)
    *     DiffNone <| DiffNone = DiffNone
    *
    * @return The oldest value from the two values.
    */
  def <|[B >: A](that: DiffOption[B]): DiffOption[B] = that |> this
}

/**
  * This represents a Diff data type where there is a  delta of a change
  *
  * @param x is the value of the data which is the change
  * @tparam A
  */
final case class DiffSome[+A](x: A) extends DiffOption[A] {
  def isEmpty: Boolean = false

  def get: A = x

  @inline def map[B](f: A => B): DiffOption[B] = DiffSome(f(x))
}

/**
  * Represents a Diff Data type where there is no change to the data
  */
case object DiffNone extends DiffOption[Nothing] {
  def isEmpty: Boolean = true

  def get: Nothing = throw new NoSuchElementException("DiffNone.get")

  @inline def map[B](f: Nothing => B): DiffOption[B] = DiffNone
}


