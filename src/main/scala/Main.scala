@main
object Main{

  testMethod
  methodWithArguments(12.0, 23.9)

  AnnotatedMethods.testMethod
  AnnotatedMethods.methodWithArguments(12.0, 23.9)

  /*
    These annotations are 'eaten' by @main annotation above. So when @main is expanded it removes two @Benchmark below,
    after reading some discussion on scalameta gitter, its considered as known bug
  */
  @Benchmark
  def testMethod[String]: Double = {
    val x = 2.0 + 2.0
    Math.pow(x, x)
  }
  @Benchmark
  def methodWithArguments(a: Double, b: Double) = {
    val c = Math.pow(a, b)
    c > a+b
  }

  val newCarMap = new Car("Silver", "Ford", 1998, "John Doe").toMap

  println(newCarMap)
}

object AnnotatedMethods {
  /*These annotations are expanded correctly, look comment above.*/

  @Benchmark
  def testMethod[String]: Double = {
    val x = 2.0 + 2.0
    Math.pow(x, x)
  }
  @Benchmark
  def methodWithArguments(a: Double, b: Double) = {
    val c = Math.pow(a, b)
    c > a+b
  }
}

