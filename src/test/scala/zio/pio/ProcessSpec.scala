package zio.pio

import zio._
import zio.console._
import zio.test._
import zio.test.Assertion._
//import zio.test.mock.MockEnvironment
import zio.test.mock._

import ProcessLog._

sealed trait EM 
case class Erm(s: String) extends EM

object ProcessLog {

  def sayHello: ZIO[Console, Nothing, Unit] =
    console.putStrLn("Hello, World!")

  def getOne: ZIO[Any, Throwable, Int] = 
    ZIO.effect(10)
}

class ProcTest(val s: String)

object ProcTest {
  def apply(s: String): ZIO[Any, Nothing, ProcTest] = 
    ZIO.succeed(new ProcTest(s))
}

object ProcessSpec extends DefaultRunnableSpec (
  suite("ProcessSpec")(
    testM("run a process") {
      for {
        //x <- ZIO.succeed(1)
        // x <- ProcessBuilder("echo", "Hello World")
        // p <- pb.jProcessBuilder.start()
        // e <- p.exitValue()
        _ <- sayHello
        s <- ProcTest("Oki")
        
        r <- MockConsole.output
      } yield
        assert(r, equalTo[Vector[String]](Vector("Hello, World!\n"))) && 
        assert(s.s, equalTo[String]("Oki")) //&&
        // assert(x.jProcessBuilder.command(), equalTo[List[String]](List("echo")))
    },
    testM("test a throwable ZIO") {
      for {
        v <- getOne.run
      } yield
        assert(v, succeeds(equalTo[Int](10)))
    }
  )
  
)
