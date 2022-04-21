package com.khtn.uaieo.model
import java.io.Serializable

class itemPartRL: Serializable  {
    var title: String?=""
    var option1: String?="";
    var option2: String?="";
    var option3: String?="";
    var option4: String?="";
    var number : Int?=0;
    var audio: String?="";
    var answer: String?=""
    var image: String?=""
    var image2: String?=""
    var image3: String?=""
    var id: String?="";
    var bookType: String?=""
    var idQuestion: String?=""
    constructor(_title: String, _option1: String, _option2: String, _option3: String, _option4: String, _answer: String,number:Int?,audio:String?,image:String?,image2:String?,image3:String?, id:String?, bookType:String?, idQuestion:String?){
        this.title = _title
        this.number=number
        this.audio=audio
        this.option1 = _option1
        this.option2 = _option2
        this.option3 = _option3
        this.option4 = _option4
        this.answer = _answer
        this.image=image
        this.image2=image2
        this.image3=image3
        this.id= id
        this.bookType = bookType
        this.idQuestion = idQuestion
    }
    constructor(){

    }

    override fun toString(): String {
        return number.toString();
    }
}