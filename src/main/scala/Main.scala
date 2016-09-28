import scala.util.{Random, Try}
@main
object Main{
  failMethod()
  testMethod
  methodWithArguments(12.0, 23.9)

  AnnotatedMethods.testMethod
  AnnotatedMethods.methodWithArguments(12.0, 23.9)

  @Benchmark
  def testMethod[String]: Double = {
    val x = 2.0 + 2.0
    Math.pow(x, x)
  }

//  issue: macro annotation now cannot parse throwing exception directly so wrapped this
//  into method.
  @RetryOnFailure(20)
  def failMethod[String](): Unit = {
    val random = Random.nextInt(10)
    println(s"evaluating random= $random")
    utils.methodThrowingException(random)
  }

  @Benchmark
  def methodWithArguments(a: Double, b: Double) = {
    val c = Math.pow(a, b)
    c > a+b
  }

  val newCarMap = Car("Silver", "Ford", 1998, "John Doe").toMap

  println(newCarMap)
}

object AnnotatedMethods {
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

