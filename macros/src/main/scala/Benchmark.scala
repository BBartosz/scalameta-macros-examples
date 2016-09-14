import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

@compileTimeOnly("@Benchmark not expanded")
class Benchmark extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"..$mods def $name[..$tparams](...$paramss): $tpeopt = $expr" =>
        q"""
          ..$mods def $name[..$tparams](...$paramss): $tpeopt = {
            val start = System.nanoTime()
            val result = $expr
            val end = System.nanoTime()
            println(${name.toString} + " elapsed time: " + (end - start) + "ns")
            result
          }
        """
      case _ => abort("@Benchmark can be annotation of method only")
    }
  }
}