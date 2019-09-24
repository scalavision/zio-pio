package zio.pio

import zio._
import scala.collection.JavaConverters._
import zio.console._
import zio.stream._

object POC {

  def createInputStream(i: java.io.InputStream) = 
    Stream.fromInputStream(i).chunks.runCollect

  def createErrStream(i: java.io.InputStream) = 
    Stream.fromInputStream(i).chunks.runCollect

  def streamOutput(stdOut: java.io.InputStream, stdErr: java.io.InputStream) = 
    (Stream.fromInputStream(stdOut).chunks ++ Stream.fromInputStream(stdErr).chunks).runCollect

  def startProcess = for {
    pb      <- ZIO.effect( new java.lang.ProcessBuilder(List("echo", "Hello world").asJava ))
    procRef       <- FiberRef.make[java.lang.Process](pb.start())
    proc    <- procRef.get
    // info       <- streamOutput(inpStr.getInputStream(), inpStr.getErrorStream())
    //  _       <- putStrLn("finished .." + info.head.map(_.toChar).mkString)
    // p       <- ZIO.effect(HackingOnTheScalaProc.p())
    // p       <- ZIO.effect(HackingOnTheScalaProc.ls())
     i        <- createInputStream(proc.getInputStream())
     p        <- createErrStream(proc.getErrorStream())
    // bytes    <- i.join
    // err      <- p.join
    _       <- putStrLn("finished .." + i.head.map(_.toChar).mkString)
    _       <- putStrLn("any errors? .." + p.toString())
    _       <- putStrLn("Exit code: " + proc.exitValue())
  } yield proc

}