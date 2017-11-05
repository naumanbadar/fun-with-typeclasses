import Dependencies._

lazy val root = Project(
  id = "fun-with-typeclasses",
  base = file("."),
  settings = Seq(
    name := "Fun with Type Classes",
    libraryDependencies += scalaTest % Test
  ) ++ inThisBuild(List(
    organization := "com.naumanbadar",
    scalaVersion := "2.12.3",
    version := "0.1.0-SNAPSHOT"
  ))
)
/*
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.3",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "Fun with Type Classes",
    libraryDependencies += scalaTest % Test
  )
*/
