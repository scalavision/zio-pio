package zio.pio

//import zio.duration._

import zio.test._
import zio.test.mock._

abstract class ZIOBaseSpec(spec: => ZSpec[MockEnvironment, Any, String, Any])
    extends DefaultRunnableSpec(spec, TimeoutStrategy.Ignore)