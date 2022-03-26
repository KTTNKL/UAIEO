package com.khtn.uaieo.model

class Vietsub {

    var title: String?=""
    var enReading: String?="";
    var viReading: String?=";"
    constructor(_title: String, _en: String, _vi: String){
        this.title = _title
        this.enReading = _en
        this.viReading = _vi
    }
    constructor(){

    }

    fun heheString(): String {
        return ""
    }
}