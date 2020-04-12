package differ.core

import differ.core.fixtures.{AwesomeModel, AwesomeModelDiff, AwesomeNestedModel, AwesomeNestedModelDiff}
import org.scalatest.{Matchers, WordSpec}


class DiffableModelSpec extends WordSpec with Matchers {

  sealed trait TestContext {
    val emptyDiff= AwesomeModelDiff()
    val diff1 = AwesomeModelDiff(
      field1 =  DiffSome("1")
    )
    val diff2 = AwesomeModelDiff(
      field1 = DiffSome("2"),
      field2 = DiffSome(2)
    )
    val diff3 = AwesomeModelDiff(
      field3 = AwesomeNestedModelDiff(DiffSome("6"))
    )

    val model1 = AwesomeModel("11", 22, AwesomeNestedModel("66"))

    val model2 = AwesomeModel("12", 22, AwesomeNestedModel("66"))

    val model3 = AwesomeModel("1", 2, AwesomeNestedModel("6"))
  }

  "DiffableModel#diff" should {
    // Please note that these tests are simply the tests of the fixture and are good code samples to show the behaviour

    "return a diff model with empty values if the values are the same for both the models" in new TestContext {
      model1.diff(model1) shouldBe AwesomeModelDiff()
    }

    "return a diff with the values that have changed" in new TestContext {
      model1.diff(model2) shouldBe AwesomeModelDiff(DiffSome(model1.field1))

      model1.diff(model3) shouldBe AwesomeModelDiff(
        DiffSome(model1.field1),
        DiffSome(model1.field2),
        AwesomeNestedModelDiff(DiffSome(model1.field3.field4))
      )
    }
  }

  "DiffableModel#applyDiff" should {
    // Please note that these tests are simply the tests of the fixture and are good code samples to show the behaviour

    "return the same model if an empty diff model is applied" in new TestContext {
      model1.applyDiff(AwesomeModelDiff()) shouldBe model1
    }

    "return a model with the changes when a non empty diffmodel is applied" in new TestContext {
      model1.applyDiff(AwesomeModelDiff(DiffSome("900"))) shouldBe model1.copy(field1 = "900")
    }
  }
}
