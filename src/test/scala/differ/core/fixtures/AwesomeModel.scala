package differ.core.fixtures

import differ.core._


case class AwesomeModel(field1: String, field2: Int, field3: AwesomeNestedModel)

case class AwesomeNestedModel(field4: String) extends DiffableModel[AwesomeNestedModelDiff, AwesomeNestedModel] {

  override def applyDiff(diff: AwesomeNestedModelDiff): AwesomeNestedModel = AwesomeNestedModel(
    diff.field4.getOrElse(this.field4)
  )

  override def diff(newerModel: AwesomeNestedModel): AwesomeNestedModelDiff = AwesomeNestedModelDiff(
    if (this.field4 == newerModel.field4) DiffNone else DiffSome(newerModel.field4)
  )
}

/**
  * DiffModel for the AwesomeModel fixture
  */
case class AwesomeModelDiff(field1: DiffOption[String] = DiffNone,
                            field2: DiffOption[Int] = DiffNone,
                            field3: AwesomeNestedModelDiff = AwesomeNestedModelDiff())
extends DiffModel[AwesomeModelDiff] {

  override def |>(that: AwesomeModelDiff): AwesomeModelDiff = AwesomeModelDiff(
    field1 = this.field1 |> that.field1,
    field2 = this.field2 |> that.field2,
    field3 = this.field3 |> that.field3
  )

  override def isEmpty: Boolean = this == AwesomeModelDiff()
}


/**
  * DiffModel for the AwesomeNestedModel fixture
  */
case class AwesomeNestedModelDiff(field4: DiffOption[String] = DiffNone) extends DiffModel[AwesomeNestedModelDiff] {

  override def |>(that: AwesomeNestedModelDiff): AwesomeNestedModelDiff =
    AwesomeNestedModelDiff(this.field4 |> that.field4)

  override def isEmpty: Boolean = this == AwesomeNestedModelDiff()
}


