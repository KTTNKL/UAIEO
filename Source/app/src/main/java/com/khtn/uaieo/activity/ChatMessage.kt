package com.khtn.uaieo.activity

import java.util.*

class ChatMessage {
    var content: String?
        get() = content
        set(value) {content = value}

    var email: String?
        get() = email
        set(value) {email=value}

    var idSender: String?
        get() = idSender
        set(value) {idSender=value}

    var time: Long?
        get() = time
        set(value) {time = value}
    constructor(content: String, email: String, idSender: String){
        this.content = content
        this.email = email
        this.idSender = idSender

        time = Date().getTime()
    }
    constructor(){
    }
}