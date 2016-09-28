# scalameta-macros-examples
http://www.bbartosz.com/blog/2016/09/16/new-scalameta-inline-macros-examples/

Examples from my repo https://github.com/BBartosz/scala-macros-examples ported to new inline macros in scalameta

@main example:
```scala
@main
object Boot {
 
}
```

expands to:

```scala
object Boot extends App {

}
```

@Benchmark example:

```scala
  @Benchmark
  def testMethod[String]: Double = {
    val x = 2.0 + 2.0
    Math.pow(x, x)
  }
```

expands to:

```scala
  def testMethod[String]: Double = {
    val start = System.nanoTime()
    val result = {
      val x = 2.0 + 2.0
      Math.pow(x, x)
    }
    val end = System.nanoTime()
    println("testMethod elapsed time: " + (end - start) + "ns")
    result
  }
```

@RetryOnFailure(n: Int) example:

```scala
  @RetryOnFailure(20)
  def failMethod[String](): Unit = {
    val random = Random.nextInt(10)
    println(s"evaluating random= $random")
    utils.methodThrowingException(random)
  }
```

expands to:

```scala
  def failMethod[String](): Unit = {
    import scala.util.Try

    for( a <- 1 to 20){
      val res = Try{
        val random = Random.nextInt(10)
        println(s"evaluating random= $random")
        utils.methodThrowingException(random)
      }
      if(res.isSuccess){
        return res.get
      }
    }
    throw new Exception("Method fails after 20 repeats")
  }
```

@Mappable example:
```scala
@Mappable
case class Car(color: String, model: String, year: Int, owner: String){
  def turnOnRadio = {
    "playing"
  }
}
```
Expands to:

```scala
case class Car(color: String, model: String, year: Int, owner: String){
  def turnOnRadio = {
    "playing"
  }
  
  def toMap: Map[String, Any] = Map("color" -> color, "model" -> model, "year" -> year, "owner" -> owner)
}
```
