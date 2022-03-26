package com.khtn.uaieo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.khtn.uaieo.R

class DetailNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val intent = intent

        val urlString = intent.getStringExtra("urlString")

        val myWebView: WebView = findViewById(R.id.webView)
        myWebView.loadUrl(urlString!!)
    }
}