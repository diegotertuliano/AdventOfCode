package Solutions

import Utils.Fs2.readFile
import Utils.IOFunctions.putStrLn
import cats.effect._
import enumeratum.values._
import io.estatico.newtype.macros.newtype

import scala.annotation.tailrec

object Day03 extends IOApp {
  import Types._

  def parseLines(lines: Vector[String]): Grid =
    lines.map(_.map(Cell.withValue).toVector)

  def numberOfTreesFound(grid: Grid, slope: Slope): Long = {
    //TODO: Don't use .value
    @tailrec
    def go(totalFound: Long, position: Position): Long =
      if (position.x.value >= grid.length) totalFound
      else {
        val found = if (grid(position.x.value)(position.y.value) == Cell.Tree) 1L else 0L
        go(totalFound + found,
           Position(X(position.x.value + slope.down.value),
                    Y((position.y.value + slope.right.value) % grid(position.x.value).length)))
      }

    go(0, Position(X(0), Y(0)))
  }

  override def run(args: List[String]): IO[ExitCode] =
    for {
      input <- readFile("input/Day03").compile.toVector
      grid = parseLines(input)
      slopes = Vector(Slope(X(1), Y(1)), Slope(X(1), Y(3)), Slope(X(1), Y(5)), Slope(X(1), Y(7)), Slope(X(2), Y(1)))
      _ <- putStrLn(slopes.map(slope => numberOfTreesFound(grid, slope)).product)
    } yield ExitCode.Success
}

object Types {
  sealed abstract class Cell(val value: Char) extends CharEnumEntry with AllowAlias
  object Cell extends CharEnum[Cell] {
    case object Open extends Cell('.')
    case object Tree extends Cell('#')

    val values: IndexedSeq[Cell] = findValues
  }

  type Grid = Vector[Vector[Cell]]

  type XType = Int
  @newtype(unapply = true) case class X(value: XType)
  type YType = Int
  @newtype(unapply = true) case class Y(value: YType)

  case class Slope(down: X, right: Y)
  case class Position(x: X, y: Y)
}
