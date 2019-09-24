package zio.pio

import zio._
import java.lang.{ ProcessBuilder => JProcessBuilder }
import scala.collection.JavaConverters._
import java.io.IOException

class ProcessBuilder private[pio] (private[pio] val jProcessBuilder: JProcessBuilder) {

  def environment(): ZIO[Any, Exception, Map[String, String]] =
    IO.effect {
        val mapJs = jProcessBuilder.environment()
        mapJs.asScala.toMap.toMap
      }
      .refineToOrDie[Exception]

  def directory(): ZIO[Any, Exception, java.io.File] =
    IO.effect(jProcessBuilder.directory())
      .refineToOrDie[Exception]

  def start(): ZIO[Any, IOException, Process] = 
    IO.effect(jProcessBuilder.start())
      .flatMap(Process.apply)
      .refineToOrDie[IOException]
}

object ProcessBuilder {

  def apply(arg: String, args: String*): ZIO[Any, Exception, ProcessBuilder] =
    IO.effect {
        val processArgs: List[String] = arg +: args.toList
        new ProcessBuilder(new JProcessBuilder(processArgs.asJava))
      }
      .refineToOrDie[Exception]

}
