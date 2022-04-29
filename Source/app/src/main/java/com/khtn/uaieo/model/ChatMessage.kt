package com.khtn.uaieo.model

import java.text.SimpleDateFormat
import java.util.*

class ChatMessage {
    var content: String?=""
    var email: String?=""
    var idSender:String?=""
    var time:String?=""
    var type=0

    constructor(content: String, email: String, idSender: String){
        this.content = content
        this.email = email
        this.idSender = idSender
        time = getDateFromMilliseconds(Date().getTime())
    }

    constructor(){
    }
    private fun getDateFromMilliseconds(millis: Long): String {
        var formatter = SimpleDateFormat("dd/MM/yyyy")
        var  dateString = formatter.format( Date(millis))

        return dateString
    }
}