package zio.pio

import java.lang.{ Process => JProcess }
import zio._
import zio.duration._
import java.util.concurrent.TimeUnit
import java.lang.InterruptedException

class Process private[pio] (private[pio] val jProcess: JProcess) {

  def waitFor(timeout: Duration): IO[InterruptedException, Boolean] =
    IO.effect {
        val t = timeout.fold(Long.MaxValue, _.nanos)
        jProcess.waitFor(t, TimeUnit.NANOSECONDS)
      }
      .refineToOrDie[InterruptedException]

  def waitFor(): IO[InterruptedException, Int] =
    IO.effect(
        jProcess.waitFor()
      )
      .refineToOrDie[InterruptedException]

  def exitValue(): IO[IllegalThreadStateException, Int] =
    IO.effect(
        jProcess.exitValue()
      )
      .refineToOrDie[IllegalThreadStateException]

  // The java api does not say anything of throwing exception
  // However it is hard to believe that this side effect will
  // not throw ...?
  def destroy(): IO[Exception, Unit] =
    IO.effect(
        jProcess.destroy()
      )
      .refineToOrDie[Exception]

  def destroyForcibly(): IO[Exception, Process] =
    IO.effect {
        jProcess.destroyForcibly()
        this
      }
      .refineToOrDie[Exception]

  def isAlive(): IO[Exception, Boolean] =
    IO.effect(
        jProcess.isAlive()
      )
      .refineToOrDie[Exception]

}

object Process {

  def apply(jP: JProcess): ZIO[Any, Nothing, Process] =
    ZIO.effectTotal(new Process(jP))
}
