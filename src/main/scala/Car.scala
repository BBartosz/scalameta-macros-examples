@Mappable
case class Car(color: String, model: String, year: Int, owner: String){
  def turnOnRadio = {
    "playing"
  }
}