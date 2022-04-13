package com.khtn.uaieo.model

class Comment {
    var content: String?=""
    var email: String?=""
    constructor(email: String, content: String){
        this.email = email
        this.content = content
    }
    constructor(){
    }
}