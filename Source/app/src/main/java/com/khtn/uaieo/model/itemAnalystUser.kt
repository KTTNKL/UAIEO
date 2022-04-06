package com.khtn.uaieo.model

import java.io.Serializable

class itemAnalystUser : Serializable {

    var id: String?=""
    var part1: Int?=0
    var part2: Int?=0
    var part3: Int?=0
    var part4: Int?=0
    var part5: Int?=0
    var part6: Int?=0
    var part7: Int?=0

    constructor(id: String?, part1: Int?,part2: Int?,part3: Int?,part4: Int?,part5: Int?,part6: Int?,part7: Int?){
        this.part1=part1;
        this.part2=part2;
        this.part3=part3;
        this.part4=part4;
        this.part5=part5;
        this.part6=part6;
        this.part7=part7;
        this.id=id
    }
    constructor(){

    }
}