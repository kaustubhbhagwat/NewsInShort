package com.example.newsinshort.ui.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsinshort.R
import com.example.newsinshort.data.database.SavedNewsViewModel
import com.example.newsinshort.data.database.model.SavedArticle

@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebViewScreen(
    article: SavedArticle,
    viewModel: SavedNewsViewModel = hiltViewModel()
){

    var backEnabled by remember { mutableStateOf(true) }
    var webView: WebView? = null

    // Adding a WebView inside AndroidView
    // with layout as full screen
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.insert(article)
                    Log.d("WebViewScreen", "WebViewScreen: CLICKED HERE")
                },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(ImageVector.vectorResource(R.drawable.ic_favorite),"")
            }
        }
    ) {

        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()

                    // to play video on a web view
                    settings.javaScriptEnabled = true

                    webViewClient = object : WebViewClient() {

                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            backEnabled = view.canGoBack()
                        }

                    }

                    loadUrl(article.url)
                    webView = this
                }
            }, update = {
                webView = it
                //  it.loadUrl(url)
            })

        BackHandler(enabled = backEnabled) {
            webView?.goBack()
        }

    }
}
