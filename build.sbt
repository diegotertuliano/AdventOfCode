import Dependencies._

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.diegotertuliano"
ThisBuild / organizationName := "diegotertuliano"

lazy val root = (project in file("."))
  .settings(
    name := "AdventOfCode",
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.fs2Core,
      Libraries.fs2Io
    )
  )
