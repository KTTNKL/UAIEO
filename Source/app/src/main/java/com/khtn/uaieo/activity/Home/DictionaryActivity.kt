package com.khtn.uaieo.activity.Home

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.khtn.uaieo.DictionaryAPI
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


        searchButton.setOnClickListener {
            symTV.text = "TỪ ĐIỂN TỪ ĐỒNG NGHĨA"
            var query= SearchBar.text.toString()
            var myDic= DictionaryAPI(this,query,translateTV)
            myDic.execute()
            webView.loadUrl("https://www.thesaurus.com/")
            webView.loadUrl("https://www.thesaurus.com/browse/"+SearchBar.text)
        }
    }
}