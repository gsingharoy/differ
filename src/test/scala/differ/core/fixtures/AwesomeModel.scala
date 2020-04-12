package differ.core.fixtures

import differ.core._


case class AwesomeModel(field1: String, field2: Int, field3: AwesomeNestedModel)
  extends DiffableModel[AwesomeModelDiff, AwesomeModel] {

  override def applyDiff(diff: AwesomeModelDiff): AwesomeModel = AwesomeModel(
    field1 = diff.field1.getOrElse(this.field1),
    field2 = diff.field2.getOrElse(this.field2),
    field3 = this.field3.applyDiff(diff.field3)
  )

  override def diff(newerModel: AwesomeModel): AwesomeModelDiff = AwesomeModelDiff(
    field1 = DiffOption.build(this.field1, newerModel.field1),
    field2 = DiffOption.build(this.field2, newerModel.field2),
    field3 = this.field3.diff(newerModel.field3)
  )
}

case class AwesomeNestedModel(field4: String) extends DiffableModel[AwesomeNestedModelDiff, AwesomeNestedModel] {

  override def applyDiff(diff: AwesomeNestedModelDiff): AwesomeNestedModel = AwesomeNestedModel(
    diff.field4.getOrElse(this.field4)
  )

  override def diff(newerModel: AwesomeNestedModel): AwesomeNestedModelDiff = AwesomeNestedModelDiff(
    DiffOption.build(this.field4, newerModel.field4)
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


