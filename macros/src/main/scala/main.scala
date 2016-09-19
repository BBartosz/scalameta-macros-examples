import scala.annotation.compileTimeOnly
import scala.meta.Ctor.Call
import scala.meta._

@compileTimeOnly("@main not expanded")
class main extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    def abortIfObjectAlreadyExtendsApp(ctorcalls: scala.collection.immutable.Seq[Call], objectName: Term) = {
      val extendsAppAlready = ctorcalls.map(_.structure).contains(ctor"App()".structure)
      if (extendsAppAlready){
        abort(s"$objectName already extends App")
      }
    }
    defn match {
      case q"..$mods object $name extends $template" => template match {
        case template"{ ..$stats1 } with ..$ctorcalls { $param => ..$stats2 }" =>
          abortIfObjectAlreadyExtendsApp(ctorcalls, name)
          val mainMethod = q"def main(args: Array[String]): Unit = { ..$stats2 }"
          val newTemplate = template"{ ..$stats1 } with ..$ctorcalls { $param => $mainMethod }"

          q"..$mods object $name extends $newTemplate"
        }
      case _ => abort("@main can be annotation of object only")
    }
  }
}