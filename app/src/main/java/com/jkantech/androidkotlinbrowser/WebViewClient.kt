/*
 * *
 *  * Created by Android Studio Canary version
 *  * User: Jonas
 *  * Date: 26/04/20
 *  * Time: 8:48 PM
 *
 *
 */

package com.jkantech.androidkotlinbrowser

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.containt_main.*
import kotlinx.android.synthetic.main.include_no_network.*
import kotlinx.android.synthetic.main.recherche.*

class WebViewClient  internal constructor(private  val activity: Activity):WebViewClient(){
    val url = "https://google.com/"
    var url_search = "https://google.com/search?q="
    private var doubleTap = false
    private lateinit var webView: WebView
    private var kaninda: SQLiteDatabase?=null



    val error_network = findViewById<LinearLayout>(R.id.error_network)
    // val  error_network=error_network as LinearLayout
    webView = findViewById<WebView>(R.id.webView)
    val  searchbar=search_bar as EditText
    //       val  framesearch=framesearch as FrameLayout
    val  btn_go=btn_go as Button
    btn_go.setOnClickListener {
        val lien=searchbar.text.toString()
        webView.visibility= View.VISIBLE
        error_network.visibility= View.GONE
        if(lien.trim()==""){



        }else{
            webView.loadUrl(url_search+lien)

        }
        //Toast.makeText(this,url+lien,Toast.LENGTH_SHORT).show()
    }

    val progressBar = findViewById<ProgressBar>(R.id.progressBar)
    progressBar.progress
    progressBar.visibility = GONE
    val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
    swipe.setColorSchemeResources(
    R.color.colorOrange,
    R.color.colorGreen,
    R.color.colorBlue,
    R.color.colorRed
    )
    swipe.setOnRefreshListener {
        webView.reload()
    }
    val swipeno = findViewById<SwipeRefreshLayout>(R.id.swipeno)
    swipe.setColorSchemeColors(
    R.color.colorOrange,
    R.color.colorGreen,
    R.color.colorBlue,
    R.color.color
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            swipeno.isRefreshing = true
            swipe.isRefreshing=true


            super.onPageStarted(view, url, favicon)
        }

        override    fun onPageFinished(view: WebView?, url: String?) {
            swipe.isRefreshing = false
            swipeno.isRefreshing = false

            if (view!=null){
                toolbar.title=view.title
            }
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            error_network.visibility = View.VISIBLE
            webView.visibility = View.GONE
            swipe.isRefreshing=false
            toast("Pas de Connection")



            super.onReceivedError(view, request, error)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            /*
            val i=Intent(Intent.ACTION_VIEW)
            if (url.startsWith("facebook.com")||url.startsWith("free.facebook.com"))
            Uri.parse("url")
            startActivity(i)
            //view.loadUrl(url);

             */

            if (url_search.startsWith("free.facebook.com") || url_search.startsWith("facebook.com")) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            } else {
                return false


            }


            return super.shouldOverrideUrlLoading(view, request)
        }
    }




}
}