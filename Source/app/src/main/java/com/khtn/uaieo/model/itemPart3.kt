package com.khtn.uaieo.model

class itemPart3 {
    var title: String?=""
    var option1: String?="";
    var option2: String?="";
    var option3: String?="";
    var option4: String?="";
    var number : Int?=0;
    var audio: String?="";
    var answer: String?=""
    constructor(_title: String, _option1: String, _option2: String, _option3: String, _option4: String, _answer: String,number:Int?,audio:String?){
        this.title = _title
        this.number=number
        this.audio=audio
        this.option1 = _option1
        this.option2 = _option2
        this.option3 = _option3
        this.option4 = _option4
        this.answer = _answer
    }
    constructor(){

    }

    override fun toString(): String {
        return number.toString();
    }
}