package zio.pio

import scala.sys.process._
import java.net.URL
import java.io.File
import scala.language.postfixOps

object HackingOnTheScalaProc {

  def p(): Int = 
    new URL("http://databinder.net/dispatch/About") #> "grep JSON" #>> new File("About_JSON") !

  def ls() = "ls" !

}