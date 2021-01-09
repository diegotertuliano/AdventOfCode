package Utils

import cats.effect.IO

import scala.io.Source

object IOFunctions {

  def getLine: IO[String] = IO(scala.io.StdIn.readLine())
  def putStr[A](s: A): IO[Unit] = IO(print(s))
  def putStrLn[A](s: A): IO[Unit] = IO(println(s))

  // `bracket`
  def readFile(filename: String): IO[Vector[String]] =
    // acquire
    IO(Source.fromFile(filename)).bracket { source =>
      // use
      IO((for (line <- source.getLines()) yield line).toVector)
    } { source =>
      // release
      IO(source.close())
    }
}
