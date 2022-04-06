package com.khtn.uaieo.model

import java.io.Serializable

class partSW : Serializable {
    var content: String?=""
    var question: String?=""
    var directions: String?=""
    var words: String?=""
    var image: String?=""
    var number: Int?=0
    constructor(content: String, question: String, image: String, number: Int){
        this.content = content
        this.question = question
        this.image = image
        this.number = number
    }
    constructor(content: String, question: String, image: String, number: Int, words: String, directions: String){
        this.content = content
        this.question = question
        this.directions = directions
        this.image = image
        this.number = number
        this.words = words
    }
    constructor(){
    }
}