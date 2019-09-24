package zio.pio

import java.io.{OutputStream => JOutputStream}
import zio._

class OutputStream private[pio](private[pio] val jOutputStream: JOutputStream) {

  

}

object OutputStream {
  
  def apply(jOutputStream: JOutputStream): Task[OutputStream] = 
    IO.effect(new OutputStream(jOutputStream))

}