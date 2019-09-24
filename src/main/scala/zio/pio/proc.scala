package zio.pio

sealed trait Environment {

  def streamToConsole = ???
  def writeToStdIn = ???
  def streamFromStdOut = ???
  def streamFromStdErr = ???
  def streamFromBoth = ???
  def stream = ???

}

final case class WithEnvironment(
  env: Map[String, String],
  propagateEnv: Boolean
) extends Environment {

}

final case class EmptyEnv() extends Environment {

}

case class RunIn(runDir: java.nio.file.Path){
  def emptyEnv = EmptyEnv()
  def withEnv(
    env: Map[String, String],
    propagateEnv: Boolean = true
  ) = WithEnvironment(
    env, propagateEnv
  )
}

object proc {

  def runIn(
    runDir: java.nio.file.Path
  ) = RunIn(runDir)

  def runInCurrentDirectory = 
    RunIn(java.nio.file.Paths.get(new java.io.File("").getCanonicalPath))

  
  // type Path = java.nio.file.Path

  

  // def setup(
  //   cwd: Path = null,
  //   env: Map[String, String] = null,
  //   stdin: ProcessInput = Pipe,
  //   stdout: ProcessOutput = Pipe,
  //   stderr: ProcessOutput = Pipe,
  //   mergeErrIntoOut: Boolean = false,
  //   propagateEnv: Boolean = true
  // ) = ???


}