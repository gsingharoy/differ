package differ.core

trait DiffModel[D <: DiffModel[_]] {

  /**
  *
  * @param that The Diff object which needs to be added
  * @return A ModelDiff object which contains the latest diff
  *         fields from both the Diff objects.
  */
  def |>(that: D): D

  /**
  *
  * @return A Boolean value specifying if the ModelDiff contains all
  *         diff fields to be non-empty.
  */
  def isEmpty: Boolean

  /**
  *
  * @return A Boolean value specifying if the ModelDiff contains atleast
  *         one diff field as non-empty.
  */
  def isDefined: Boolean = !isEmpty
}

