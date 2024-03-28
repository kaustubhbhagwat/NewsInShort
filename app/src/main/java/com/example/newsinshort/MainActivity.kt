package com.example.newsinshort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.newsinshort.ui.navigation.AppNavigationGraph
import com.example.newsinshort.ui.theme.NewsInShortTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsInShortTheme {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint(){
    AppNavigationGraph()
}

@Composable
fun NewsInShortEntryPoint(){

}