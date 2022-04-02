package com.khtn.uaieo.model

import java.io.Serializable

class itemExamRL : Serializable {
    var bookType: String?=""
    var test: Int?=0;
    var year: Int?=0;
    var id: String?=""
    constructor(bookType: String, test: Int, year: Int,id: String?){
        this.bookType = bookType
        this.test = test
        this.year = year
        this.id=id
    }
    constructor(){

    }
}