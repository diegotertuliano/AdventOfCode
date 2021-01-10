package Solutions

import Utils.Fs2.readFile
import Utils.IOFunctions.putStrLn
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import cats.parse._

case class Policy(lowest: Int, highest: Int, letter: Char)
case class Input(policy: Policy, password: String)

object Day02 extends IOApp {
  object ParserRules {
    val digit: Parser1[Int] = Numbers.digits1.map(a => a.toInt)
    val lowercaseLetter: Parser1[Char] = Parser.charIn('a' to 'z')

    val discard1: Parser1[Unit] = Parser.char('-')
    val discard2: Parser1[Unit] = Parser.char(' ')
    val discard3: Parser1[Unit] = Parser.string1(": ")
  }

  def parser: Parser1[Input] = {
    import ParserRules._

    val policy: Parser1[Policy] = (
      digit ~
        (discard1 *> digit) ~
        (discard2 *> lowercaseLetter)
    ).map {
      case ((lowest, highest), letter) =>
        Policy(lowest, highest, letter)
    }

    val password = discard3 *> lowercaseLetter.rep1.string

    (policy ~ password).map { case (policy, password) => Input(policy, password) }
  }

  def parseLines(lines: List[String]): Either[Parser.Error, List[Input]] =
    lines
      .map(parser.parse)
      .traverse(_.map { case (_, input) => input })

  def checkNumberValidPasswordsPart1(parsedLines: List[Input]): Int =
    parsedLines.count {
      case Input(policy, password) =>
        val nTimes = password.count(_ == policy.letter)
        (policy.lowest <= nTimes) && (nTimes <= policy.highest)
    }

  def checkNumberValidPasswordsPart2(parsedLines: List[Input]): Int =
    parsedLines.count {
      case Input(policy, password) =>
        (password(policy.lowest - 1) == policy.letter) ^ (password(policy.highest - 1) == policy.letter)
    }

  override def run(args: List[String]): IO[ExitCode] =
    for {
      input <- readFile("input/Day02").compile.toList
      parsedLines = parseLines(input)
      _ <- putStrLn(parsedLines.map(checkNumberValidPasswordsPart1))
      _ <- putStrLn(parsedLines.map(checkNumberValidPasswordsPart2))
    } yield ExitCode.Success
}
