package com.khtn.uaieo.activity.Home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.khtn.uaieo.DictionaryAPI
import com.khtn.uaieo.R
import kotlinx.android.synthetic.main.activity_dictionary.*


class DictionaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

//        webView.settings.setJavaScriptEnabled(true)
//
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                url?.let { view?.loadUrl(it) }
//                return true
//            }
//        }
//        webView.loadUrl("https://dictionary.cambridge.org/vi/dictionary/english/dictionary")
//        searchButton.setOnClickListener {
//            webView.loadUrl("https://dictionary.cambridge.org/vi/dictionary/english/"+SearchBar.text)
//        }
        searchButton.setOnClickListener {
            var query= SearchBar.text.toString()
            var myDic= DictionaryAPI(this,query,translateTV)
            myDic.execute()
        }
    }
}