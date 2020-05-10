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

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.solver.GoalRow
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.containt_main.*
import kotlinx.android.synthetic.main.include_no_network.*
import kotlinx.android.synthetic.main.recherche.*
import android.view.MenuItem as ViewMenuItem

class MainActivity : AppCompatActivity() {
    //private var url: String? = null
    val url = "https://google.com/"
    var url_search = "https://google.com/search?q="
    private var doubleTap = false
    private lateinit var webView: WebView
    private var kaninda:SQLiteDatabase?=null
    private lateinit var  searchView:SearchView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
       val error_network = findViewById<LinearLayout>(R.id.error_network)
      // val  error_network=error_network as LinearLayout
        webView = findViewById<WebView>(R.id.webView)
       val  searchbar=search_bar as EditText
//       val  framesearch=framesearch as FrameLayout
       val  btn_go=btn_go as Button
        btn_go.setOnClickListener {
            val lien=searchbar.text.toString()
            webView.visibility= VISIBLE
            error_network.visibility= GONE
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
            R.color.colorRed
        )
        swipeno.setOnRefreshListener {
            swipeno.isRefreshing=false
            webView.reload()
        }

        //val btn_reessayer = findViewById<Button>(R.id.btn_reessayer)
      val   btn_reessayer=btn_reessayer as Button

        btn_reessayer.setOnClickListener {
            webView.visibility= VISIBLE
            webView.reload()
            swipe.isRefreshing = true
            error_network.visibility = GONE

        }



        WebAction()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    fun WebAction() {
        swipe.isRefreshing = true

        /*
        val intent = intent
        if (null != intent) {
            url = getIntent().data.toString()


        }

         */
        val settings = webView.settings
        settings.setAppCacheEnabled(false)
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true
        settings.defaultTextEncodingName
        settings.safeBrowsingEnabled
        webView.certificate
        webView.favicon
        webView.title

        settings.loadWithOverviewMode = true

        // settings.databaseEnabled
        settings.setAppCacheEnabled(true)
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
        else if (doubleTap) {
            webView.clearCache(true)

            super.onBackPressed()
        }else{
            doubleTap=true
            toast("Appuyez encors pour quitter")
            val handler=Handler()
            handler.postDelayed(Runnable { doubleTap=false },2000)

        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

























    override fun onOptionsItemSelected(item: ViewMenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refrech -> {
                toast("Actualisation...")
                webView.reload()
                error_network.visibility = GONE
                /*
                if (error_network.visibility != -1) {
                    error_network.visibility = GONE
                }
                error_network.visibility = GONE

            }

                 */
            }
            R.id.action_about->{
               val alertDialog=AlertDialog.Builder(this)
                alertDialog.setTitle("A propos")
                alertDialog.setMessage("developper jkanTech \n @2020")
                alertDialog.show()

            }
            R.id.action_exit->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)

    }



    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })
    }

    override fun onRestart() {
        webView.loadUrl(url)

        super.onRestart()
    }

    override fun onResume() {
        webView.reload()
        super.onResume()
    }

    override fun onPause() {
        webView.onPause()

        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun btn_back(view: View) {
        if (webView.canGoBack()){
            webView.goBack()
            swipe.isRefreshing=true
        }else
            finish()
    }

    fun btn_forward(view: View) {
        if (webView.canGoForward()){
            webView.goForward()
            swipe.isRefreshing=true

        }else
            webView.reload()

    }
    fun btn_home(view: View) {
        swipe.isRefreshing=true
        webView.clearHistory()
        webView.loadUrl(url)
    }


}

private fun Handler.postDelayed(i: Int) {


}


