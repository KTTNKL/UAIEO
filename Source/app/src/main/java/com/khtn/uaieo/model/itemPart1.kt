package com.khtn.uaieo.model

class itemPart1 {
    var audio: String?=""
    var image: String?="";
    var number: Int?=0;
    var answer: String?=";"
    constructor(audio: String, image: String, number: Int?, _answer: String){
        this.audio = audio
        this.image = image
        this.number = number
        this.answer = _answer
    }
    constructor(){

    }
}