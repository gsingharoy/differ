import sbt.url

name := "differ"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions in ThisBuild += "-Ypartial-unification"

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.scalamock" %% "scalamock" % "4.1.0" % Test
)

libraryDependencies ++= testDependencies

lazy val root = project
  .in(file("."))
