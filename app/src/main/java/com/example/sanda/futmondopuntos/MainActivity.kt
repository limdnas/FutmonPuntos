package com.example.sanda.futmondopuntos

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.example.sanda.futmondopuntos.R
import com.example.sanda.futmondopuntos.R.layout.activity_main
import java.io.BufferedReader


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var imageView: ImageView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        webView = findViewById<WebView>(R.id.webView) as WebView
        webView = findViewById<WebView>(R.id.webView) as WebView

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                injectJavaScript(view)
                injectCSS()

            }
        }
        webView.loadUrl("http://www.futbolfantasy.com/laliga/puntos")

        Handler().postDelayed({
            findViewById<View>(R.id.imageView).visibility = (View.GONE)
            findViewById<View>(R.id.webView).visibility = View.VISIBLE
        }, 7000)
    }

    private fun injectJavaScript(view: WebView): Boolean {

        view.loadUrl("javascript:(function() { " +
                "var footer = document.getElementsByTagName('footer')[0];"
                + "footer.parentNode.removeChild(footer);" +
                "})()")
        /*view.loadUrl("javascript:(function() { " +
                "var element1 = document.getElementsByClassName('adsbygoogle');"
                + "element1.parentNode.removeChild(element1);" +
                "})()")
        view.loadUrl("javascript:(function() { " +
                "var element = document.getElementById('main_header');"
                + "element.parentNode.removeChild(element);" +
                "})()")
        view.loadUrl("javascript:(function() { " +
                "var nav = document.getElementsByTagName('nav')[0];"
                + "nav.parentNode.removeChild(nav);" +
                "})()")
        view.loadUrl("javascript:(function() { " +
                "var set = document.getElementsByClassName('adsbygoogle','main_header');"
                + "set[0].style.margin = '-3000px';" +
                "})()")*/
        return true
    }

    private fun injectCSS() {
        try {
            val inputStream = resources.openRawResource(R.raw.style)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            val encoded = Base64.encodeToString(buffer, Base64.NO_WRAP)
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()")

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
