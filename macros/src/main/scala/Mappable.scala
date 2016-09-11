import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

@compileTimeOnly("@Mappable not expanded")
class Mappable extends StaticAnnotation {
  inline def apply(defn: Defn) = meta {
    defn match {
      case q"..$mods class $tname[..$tparams] (...$paramss) extends $template" =>
        template match {
          case template"{ ..$stats } with ..$ctorcalls { $param => ..$body }" => {
            val expr = paramss.flatten.map(p => q"${p.name.toString}").zip(paramss.flatten.map{ p =>
                /*p.name and Term.Name(...) in theory has the same structure, but there is compilation
                 error when trying to return p.name, not sure why, probably shouldnt use show[Structure] to compare this
                 code, and moreover according to quasiquotes docs: anonymous names can't be constructed,
                 only extracted from param so this is why have to write this way*/

//                println(p.name.show[Structure] == Term.Name(p.name.toString).show[Structure])
                Term.Name(p.name.toString)
            }).map{case (paramName, paramTree) => {
              /*convert from scala.Tuple to scala.meta.Term$Tuple$TermTupleImpl*/
              q"($paramName, $paramTree)"
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