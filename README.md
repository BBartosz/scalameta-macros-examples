# scalameta-macros-examples
Examples from my repo https://github.com/BBartosz/scala-macros-examples ported to new inline macros in scalameta


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
@Mappable
case class Car(color: String, model: String, year: Int, owner: String){
  def turnOnRadio = {
    "playing"
  }
  
  def toMap: Map[String, Ant] = Map("color" -> color, "model" -> model, "year" -> year, "owner" -> owner)
}
```

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
