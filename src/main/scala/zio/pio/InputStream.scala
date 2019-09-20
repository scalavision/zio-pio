package zio.pio

import java.io.{ InputStream => JInputStream }
import zio._
import java.io.IOException

class InputStream private[pio] (private[pio] val jInputStream: JInputStream) {

  def read() = IO.effect(jInputStream.read()).refineToOrDie[IOException]

}

object InputStream {

  def apply(jInputStream: JInputStream) =
    IO.effect(new InputStream(jInputStream))
}
