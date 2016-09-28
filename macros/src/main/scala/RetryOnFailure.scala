import scala.annotation.compileTimeOnly
import scala.meta._
import scala.util.Try

@compileTimeOnly("@RetryOnFailure not expanded")
class RetryOnFailure(repeat: Int) extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"..$mods def $name[..$tparams](...$paramss): $tpeopt = $expr" => {
        val q"new $_(${arg})" = this
        val repeats = Try(arg.toString.toInt).getOrElse(abort(s"Retry on failure takes number as parameter"))

        val newCode =
          q"""..$mods def $name[..$tparams](...$paramss): $tpeopt = {
                import scala.util.Try

                for( a <- 1 to $repeats){
                  val res = Try($expr)
                  if(res.isSuccess){
                    return res.get
                  }
                }

                throw new Exception("Method fails after "+$repeats + " repeats")
              }
            """
        newCode
      }
      case _ => abort("@RetryOnFailure can be annotation of method only")
    }
  }
}