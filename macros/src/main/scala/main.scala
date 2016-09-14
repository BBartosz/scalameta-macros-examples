import scala.annotation.compileTimeOnly
import scala.meta._

@compileTimeOnly("@main not expanded")
class main extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"..$mods object $name extends $template" => template match {
        case template"{ ..$stats1 } with ..$ctorcalls { $param => ..$stats2 }" =>

          val mainMethod = q"def main(args: Array[String]): Unit = { ..$stats2 }"
          val newTemplate = template"{ ..$stats1 } with ..$ctorcalls { $param => $mainMethod }"

          q"..$mods object $name extends $newTemplate"
        }
      case _ => abort("@main can be annotation of object only")
    }
  }
}