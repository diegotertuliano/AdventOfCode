import sbt._

object Dependencies {

  object Versions {
    val cats = "2.3.1"
    val catsEffect = "2.3.1"
    val catsParse = "0.2.0"
    val fs2 = "2.5.0"
    val enumeratum = "1.6.1"
    val newtype = "0.4.4"
  }

  object Libraries {
    val cats = "org.typelevel" %% "cats-core" % Versions.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect
    val catsParse = "org.typelevel" %% "cats-parse" % Versions.catsParse
    val fs2Core = "co.fs2" %% "fs2-core" % Versions.fs2
    val fs2Io = "co.fs2" %% "fs2-io" % Versions.fs2
    val enumeratum = "com.beachape" %% "enumeratum" % Versions.enumeratum
    val newtype = "io.estatico" %% "newtype" % Versions.newtype
  }

}
