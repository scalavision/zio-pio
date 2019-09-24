package zio.pio

import zio._
import zio.console._
import zio.App

object Main extends App {
  override def run(args: List[String]): ZIO[zio.blocking.Blocking with Console, Nothing, Int] = 
    POC.startProcess.fold(_ => 1, _ => 0)
}