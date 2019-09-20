package zio.pio

import testz.{ Harness, assert }
import zio._

class ProcessBuilderSpec extends DefaultRuntime {

  def tests[T](harness: Harness[T]): T = {
    import harness._

    section(test("process builder") { () =>
      assert(1 == 1)
    })

  }

}
