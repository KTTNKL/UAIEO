package com.khtn.uaieo.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.khtn.uaieo.R


class DictionaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        val searchView: SearchView = findViewById(R.id.searchView)
        val webView: WebView = findViewById(R.id.webView)
//        webView.settings.javaScriptEnabled = true
////2
////        webView.webViewClient = MyWebViewClient(this, searchView, "hcmus.edu.vn")
////3
//        webView.loadUrl("https://www.hcmus.edu.vn")
    }
}