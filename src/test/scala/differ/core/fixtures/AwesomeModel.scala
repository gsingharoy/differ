package differ.core.fixtures

import differ.core._

/**
  * Here you can check some sample models which uses the traits DiffModel and DiffableModel
  *
  * These models then eventually will have operations to have diffs and build from diffs.
  *
  * You can even build a sequence of Diffs with
  *
  * val diff: AwesomeModelDiffs = diffs.foldLeft(AwesomeModel())(_ |> _)
  *
  */

/**
  * This is a test fixture model. This has a corresponding DiffModel
  */
case class AwesomeModel(field1: String, field2: Int, field3: AwesomeNestedModel)
  extends DiffableModel[AwesomeModelDiff, AwesomeModel] {

  override def applyDiff(diff: AwesomeModelDiff): AwesomeModel = AwesomeModel(
    field1 = diff.field1.getOrElse(this.field1),
    field2 = diff.field2.getOrElse(this.field2),
    field3 = this.field3.applyDiff(diff.field3)
  )

  override def diff(olderModel: AwesomeModel): AwesomeModelDiff = AwesomeModelDiff(
    field1 = DiffOption.build(olderModel.field1, this.field1),
    field2 = DiffOption.build(olderModel.field2, this.field2),
    field3 = this.field3.diff(olderModel.field3)
  )
}

/**
  *
  * This is a test fixture model which is nested in the previous model. This has a corresponding DiffModel
  */
case class AwesomeNestedModel(field4: String) extends DiffableModel[AwesomeNestedModelDiff, AwesomeNestedModel] {

  override def applyDiff(diff: AwesomeNestedModelDiff): AwesomeNestedModel = AwesomeNestedModel(
    diff.field4.getOrElse(this.field4)
  )

  override def diff(olderModel: AwesomeNestedModel): AwesomeNestedModelDiff = AwesomeNestedModelDiff(
    DiffOption.build(olderModel.field4, this.field4)
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


