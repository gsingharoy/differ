package differ.core

import differ.core.fixtures.{AwesomeModelDiff, AwesomeNestedModelDiff}
import org.scalatest.{Matchers, WordSpec}


class DiffModelSpec extends WordSpec with Matchers {

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
  }

  "DiffModel#|>" should {
    // Please note that these tests are simply the tests of the fixture and are good code samples to show the behaviour
    "apply a proper |> of DiffOption" in new TestContext {

      Seq(diff1, diff2, diff3).foreach{ v =>
        v |> emptyDiff shouldBe v
        emptyDiff |> v shouldBe v
      }

      diff1 |> diff2 shouldBe AwesomeModelDiff(diff2.field1, diff2.field2)

      diff1 |> diff2 |> diff1 shouldBe AwesomeModelDiff(diff1.field1, diff2.field2)

      diff2 |> diff3 shouldBe AwesomeModelDiff(diff2.field1, diff2.field2, diff3.field3)

      diff1 |> diff3 shouldBe AwesomeModelDiff(diff1.field1, DiffNone, diff3.field3)
    }
  }

  "DiffModel#isEmpty" should {
    // Please note that these tests are simply the tests of the fixture and are good code samples to show the behaviour

    "return false when the DiffModel has some value" in new TestContext {
      Seq(diff1, diff2, diff3).foreach{ v =>
        v.isEmpty shouldBe false
      }
    }

    "return true when it is an empty Diff" in {
      AwesomeModelDiff().isEmpty shouldBe true
    }
  }

  "DiffModel#isDefined" should {
    // Please note that these tests are simply the tests of the fixture and are good code samples to show the behaviour

    "return true when the DiffModel has some value" in new TestContext {
      Seq(diff1, diff2, diff3).foreach{ v =>
        v.isDefined shouldBe true
      }
    }

    "return false when it is an empty Diff" in {
      AwesomeModelDiff().isDefined shouldBe false
    }
  }

}
