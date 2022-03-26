package com.khtn.uaieo.activity

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.khtn.uaieo.R
import kotlinx.android.synthetic.main.activity_dictionary.*


class DictionaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        webView.settings.setJavaScriptEnabled(true)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }
        }
        webView.loadUrl("https://dictionary.cambridge.org/vi/dictionary/english/dictionary")
        searchButton.setOnClickListener {
            webView.loadUrl("https://dictionary.cambridge.org/vi/dictionary/english/"+SearchBar.text)
        }
    }
}