package com.example.newsinshort.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.newsinshort.ui.navigation.AppNavigationGraph
import com.example.newsinshort.ui.theme.NewsInShortTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NewsInShortTheme {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint() {
    AppNavigationGraph()
}

@Composable
fun NewsInShortEntryPoint() {

}