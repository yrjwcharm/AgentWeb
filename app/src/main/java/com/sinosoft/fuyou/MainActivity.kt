package com.sinosoft.fuyou

import AndroidInterface
import android.app.Activity
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.sinosoft.fuyou.databinding.ActivityMainBinding
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.just.agentweb.WebViewClient

class MainActivity : AppCompatActivity() {
    var mAgentWeb: AgentWeb? = null;
    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view);
        initData();

    }

    private fun initData() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.view, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebViewClient(mWebViewClient).setWebChromeClient(mWebChromeClient)
            .createAgentWeb()
            .ready()
            .go("https://www.baidu.com");
        val webSetting = mAgentWeb?.webCreator?.webView?.settings
        webSetting?.javaScriptEnabled = true
        //此处为agentweb声明js方法
        mAgentWeb?.jsInterfaceHolder?.addJavaObject("android", AndroidInterface(this, mAgentWeb!!))
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted")
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
        }
    }

    fun getUrl(): String? {
        return "https://m.jd.com/"
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb?.let { it.handleKeyEvent(keyCode, event) } == true) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}