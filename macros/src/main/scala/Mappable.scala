import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

@compileTimeOnly("@Mappable not expanded")
class Mappable extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"..$mods class $tname[..$tparams] (...$paramss) extends $template" =>
        template match {
          case template"{ ..$stats } with ..$ctorcalls { $param => ..$body }" => {
            val expr = paramss.flatten.map(p => q"${p.name.toString}").zip(paramss.flatten.map{
              case param"..$mods $paramname: $atpeopt = $expropt" => paramname
            }).map{case (q"$paramName", paramTree) => {
              /*why we need to write this way? I think answer is in quasiqutes docs:
              anonymous names can't be constructed, only extracted from param. This is why we need to cheat.*/
              q"(${Term.Name(paramName.toString)}, ${Term.Name(paramTree.toString)})"
            }}

            val newBody = body :+ q"""def toMap: Map[String, Any] = Seq(..$expr).toMap"""
            val newTemplate = template"{ ..$stats } with ..$ctorcalls { $param => ..$newBody }"

            q"..$mods class $tname[..$tparams] (...$paramss) extends $newTemplate"
          }
        }
      case _ => throw new Exception("@Mappable can be annotation of class only")
    }
  }
}