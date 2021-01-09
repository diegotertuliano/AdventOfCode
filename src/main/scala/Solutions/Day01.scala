package Solutions

import Utils.Fs2.readFileAsIntStream
import Utils.IOFunctions.putStrLn
import cats.effect._

import scala.annotation.tailrec

object Day01 extends IOApp {

  def findTwoSum(totalSum: Int, input: List[Int]): Option[(Int, Int)] = {

    @tailrec
    def go(xs: List[Int], set: Set[Int] = Set.empty): Option[(Int, Int)] =
      xs match {
        case x :: xs =>
          val difference = totalSum - x

          if (set.contains(difference))
            Some((x, difference))
          else
            go(xs, set + x)
        case Nil => None
      }

    go(input)

  }

  def findThreeSum(totalSum: Int, input: List[Int]): Option[Int] = {

    @tailrec
    def go(xs: List[Int]): Option[Int] =
      xs match {
        case x :: xs =>
          val difference = totalSum - x
          val maybeTwoSum = findTwoSum(difference, input)

          maybeTwoSum match {
            case Some((n1, n2)) => Some(x * n1 * n2)
            case None           => go(xs)
          }

        case Nil => None
      }

    go(input)

  }

  override def run(args: List[String]): IO[ExitCode] =
    for {
      input <- readFileAsIntStream("input/Day01").compile.toList
      _ <- putStrLn(findTwoSum(2020, input).map { case (x, y) => x * y })
      _ <- putStrLn(findThreeSum(2020, input))
    } yield ExitCode.Success
}
