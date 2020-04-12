package differ.core

import org.scalatest.{Matchers, WordSpec}


class DiffOptionSpec extends WordSpec with Matchers {

  "DiffSome#isDefined" should {
    "return true" in {
      DiffSome(1).isDefined shouldBe true
    }
  }

  "DiffNone#isDefined" should {
    "return false" in {
      DiffNone.isDefined shouldBe false
    }
  }

  "DiffSome#isEmpty" should {
    "return false" in {
      DiffSome(1).isEmpty shouldBe false
    }

  }
  "DiffNone#isEmpty" should {
    "return true " in {
      DiffNone.isEmpty shouldBe true
    }
  }

  "DiffSome#map" should {
    "call the function appropriately" in {
      DiffSome(3).map(_ * 5) shouldBe DiffSome(15)

      DiffSome("45").map(_.toInt) shouldBe DiffSome(45)
    }
  }

  "DiffNone#map" should {
    "return DiffNone" in {
      DiffNone.map(_ => 3) shouldBe DiffNone
    }
  }

  "DiffSome#get" should {
    "return the value in the DiffSome" in {
      DiffSome(3).get shouldBe 3

      DiffSome(BigDecimal(45)).get shouldBe BigDecimal(45)
    }
  }

  "DiffNone#get" should {
    "throw an error" in {
      // This behaviour is kept similar to how the #get method behaves in Option
      val err = intercept[NoSuchElementException]{
        DiffNone.get
      }

      err.getMessage shouldBe "DiffNone.get"
    }
  }

  "DiffSome#getOrElse" should {
    "return the value" in {
      DiffSome(3).getOrElse(4) shouldBe 3
    }
  }

  "DiffNone#getOrElse" should {
    "return the default value" in {
      val x: DiffOption[Int] = DiffNone

      x.getOrElse(3) shouldBe 3
    }
  }

  "DiffSome#toList" should {
    "return a List with a singleton value" in {
      DiffSome(89).toList shouldBe List(89)

      DiffSome("89").toList shouldBe List("89")
    }
  }

  "DiffOption#|>" should {
    "return the DiffSome value if one of the values is a DiffSome" in {
      DiffNone |> DiffSome(3) shouldBe DiffSome(3)

      DiffSome(3) |> DiffNone shouldBe DiffSome(3)
    }
    "return the last value when the last and first are DiffSome" in {
      DiffSome(3) |> DiffSome(4) shouldBe DiffSome(4)
    }

    "return DiffNone if both the values are DiffNone" in {
      DiffNone |> DiffNone shouldBe DiffNone
    }
  }

  "DiffOption#<|" should {
    "return the DiffSome value if one of the values is a DiffSome" in {
      DiffNone <| DiffSome(3) shouldBe DiffSome(3)

      DiffSome(3) <| DiffNone shouldBe DiffSome(3)
    }
    "return the first value when the last and first are DiffSome" in {
      DiffSome(3) <| DiffSome(4) shouldBe DiffSome(3)
    }

    "return DiffNone if both the values are DiffNone" in {
      DiffNone <| DiffNone shouldBe DiffNone
    }
  }

  "DiffOption.build" should {
    "return DiffNone if the values are same" in {
      DiffOption.build(3, 3) shouldBe DiffNone
    }

    "return DiffSome if the values are not same" in {
      DiffOption.build(3, 4) shouldBe DiffSome(4)
    }
  }
}
