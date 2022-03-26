package com.khtn.uaieo.model

class NewsModel {
    var title: String? =""
    var image: String? =""
    var url: String? = ""

    constructor(){
    }

    constructor(_title: String, _image: String, _url: String){
        this.title = _title
        this.image = _image
        this.url = _url
    }

}

