package differ.core

/**
  * Represents the behaviour where a model can be Diffable.
  * This can be done if an equivalent DiffModel for the model exists.
  *
  * @tparam D DiffModel class
  * @tparam M A DiffableModel class
  */
trait DiffableModel[D <: DiffModel[_], M <: DiffableModel[D, _]] {

  /**
    *
    * @param diff The DiffModel which needs to be used to apply the diffs to the model
    * @return An Updated Model which contains the changes of the Diff applied
    */
  def applyDiff(diff: D): M

  /**
    *
    * @param olderModel The other DiffableModel with which diff will be figured out.
    *              Priority is given to the values of `this` and not `otherModel`.
    *
    * @return A ModelDiff object containing the diffs of the two objects.
    */
  def diff(olderModel: M): D
}

