package Utils

import cats.effect.{Blocker, ContextShift, IO}
import fs2.{io, text, Stream}
import java.nio.file.Paths

object Fs2 {

  implicit val ioContextShift: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)

  def readFileAsIntStream(filename: String): Stream[IO, Int] = Stream.resource(Blocker[IO]).flatMap { blocker =>
    io.file
      .readAll[IO](Paths.get(filename), blocker, 4096)
      .through(text.utf8Decode)
      .through(text.lines)
      .map(_.toInt)
  }

}
