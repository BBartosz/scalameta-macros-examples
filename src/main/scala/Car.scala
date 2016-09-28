@Mappable
case class Car(color: String, model: String, year: Int, owner: String){
  def turnOnRadio = {
    "playing"
  }
}

object utils {
  def methodThrowingException(random: Int): Unit = {
    if(random%2 == 0){
      throw new Exception(s"throwing exception for ${random}")
    }
  }
}