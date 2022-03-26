package com.khtn.uaieo.model

class NewsModel {
    var title: String? = ""
    var image: String? = ""
    var url: String? = ""

    constructor(){
    }

    constructor(title: String, image: String, url: String){
        this.title = title
        this.image = image
        this.url = url
    }

}

