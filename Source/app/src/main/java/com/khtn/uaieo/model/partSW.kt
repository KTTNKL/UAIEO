package com.khtn.uaieo.model

import java.io.Serializable

class partSW : Serializable {
    var content: String?=""
    var question: String?=""
    var image: String?=""
    var number: Int?=0
    constructor(content: String, question: String, image: String, number: Int){
        this.content = content
        this.question = question
        this.image = image
        this.number = number
    }
    constructor(){
    }
}