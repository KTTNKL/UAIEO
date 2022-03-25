package com.khtn.uaieo.model

//data class itemQuiz (
//    var title: String,
//    var option1: String,
//    var option2: String,
//    var option3: String,
//    var option4: String,
//    var answer: String
//        )

class itemQuiz {

    var title: String?=""
    var option1: String?="";
    var option2: String?="";
    var option3: String?="";
    var option4: String?="";
    var answer: String?=";"
    constructor(_title: String, _option1: String, _option2: String, _option3: String, _option4: String, _answer: String){
        this.title = _title
        this.option1 = _option1
        this.option2 = _option2
        this.option3 = _option3
        this.option4 = _option4
        this.answer = _answer
    }
    constructor(){

    }
}
