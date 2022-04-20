package com.khtn.uaieo

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Dic(context: Context, query: String, textView: TextView) : AsyncTask<String, Int, String>(){

    var myWord= query
    var textResult=textView
    override fun doInBackground(vararg p0: String?): String {
        val language = "en-gb"
        val word = myWord
        val fields = "definitions"
        val strictMatch = "false"
        val word_id = word
        val restUrl =
            "https://od-api.oxforddictionaries.com:443/api/v2/entries/$language/$word_id?fields=$fields&strictMatch=$strictMatch"
        val app_id = "40754125"
        val app_key = "cf8756ed2c99b31228689249b14de50e"
        try {
            val url = URL(restUrl)
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.setRequestProperty("Accept", "application/json")
            urlConnection.setRequestProperty("app_id", app_id)
            urlConnection.setRequestProperty("app_key", app_key)

            // read the output from the server
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val stringBuilder = StringBuilder()
            var line: String? = null
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(
                    """
                $line
                
                """.trimIndent()
                )
            }
            return (stringBuilder.toString())
        } catch (e: IOException) {
            return e.printStackTrace().toString()
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)


        try{
        var js: JSONObject= JSONObject(result)
        var result= js.getJSONArray("results");

        var lEntries= result.getJSONObject(0)
        var laArray= lEntries.getJSONArray("lexicalEntries")

        var entries= laArray.getJSONObject(0);
        var e= entries.getJSONArray("entries");

        var json=e.getJSONObject(0);
        var senseArray=json.getJSONArray("senses");

        var de =senseArray.getJSONObject(0);
        var d =de.getJSONArray("definitions");

        var def= d.getString(0);

        textResult.text=def
        }catch (e: JSONException){
            textResult.text="No result"
        }
    }

}