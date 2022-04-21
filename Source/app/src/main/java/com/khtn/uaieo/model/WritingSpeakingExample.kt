package com.khtn.uaieo.model

import java.io.Serializable

class WritingSpeakingExample : Serializable {
    var email: String?=""
    var id: String?=""
    var image: String?=""
    constructor(email: String, id: String, image: String){
        this.email = email
        this.id = id
        this.image = image
    }
    constructor(){
    }
}