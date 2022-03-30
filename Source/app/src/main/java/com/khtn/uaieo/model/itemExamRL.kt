package com.khtn.uaieo.model

class itemExamRL {
    var bookType: String?=""
    var test: Int?=0;
    var year: Int?=0;
    constructor(bookType: String, test: Int, year: Int){
        this.bookType = bookType
        this.test = test
        this.year = year
    }
    constructor(){

    }
}