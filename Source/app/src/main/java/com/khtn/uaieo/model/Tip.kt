package com.khtn.uaieo.model

class Tip {

    var title: String?=""
    var content: String?=";"
    var subtitle: String?=";"
    constructor(_title: String, _content: String){
        this.title = _title
        this.content = _content
        this.subtitle = _content.take(20) + "..."
    }
    fun createSubtitle(){
        this.subtitle = (content?.take(20) ?: "") + "..."
    }
    constructor(){

    }
}