import Dependencies._

lazy val root = Project(
  id = "fun-with-typeclasses",
  base = file("."),
  settings = Seq(
    name := "Fun with Type Classes",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      cats
    ),
    scalacOptions ++= Seq(
      "-Xfatal-warnings",
      "-Ypartial-unification"
    )
  ) ++ inThisBuild(
    List(
      organization := "com.naumanbadar",
      scalaVersion := "2.12.3",
      version := "1.0-SNAPSHOT"
    ))
)
