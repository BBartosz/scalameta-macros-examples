/*We defined Car as case class but somehow when @Mappable parses code of case class it doesnt see case modifier*/
@Mappable
case class Car(color: String, model: String, year: Int, owner: String){
  def turnOnRadio = {
    "playing"
  }
}