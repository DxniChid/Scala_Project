ThisBuild / scalaVersion := "3.7.3"

lazy val root = (project in file("."))
  .settings(
    name := "Scala_Poker_Simulator",
    Compile / scalaSource := baseDirectory.value,
    Compile / unmanagedSourceDirectories := Seq(baseDirectory.value),
    Test / scalaSource := baseDirectory.value / "test"
  )