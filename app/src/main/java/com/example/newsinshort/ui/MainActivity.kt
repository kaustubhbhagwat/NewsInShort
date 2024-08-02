package com.example.newsinshort.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsinshort.NewsApplication.Companion.TAG
import com.example.newsinshort.notifications.ChatViewModel
import com.example.newsinshort.ui.navigation.NavGraphSetup
import com.example.newsinshort.ui.screens.home_screen.HomeScreen
import com.example.newsinshort.ui.screens.onboarding_screen.OnboardingScreen
import com.example.newsinshort.ui.theme.NewsInShortTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.messaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Declare the launcher at the top of your Activity/Fragment:

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ChatViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        logRegToken()
        setContent {
            NewsInShortTheme {
                val navController = rememberNavController()

                val sharedPreferences = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
                val isOnboardingComplete = sharedPreferences.getBoolean("isFinished", false)

                if (isOnboardingComplete) {
                    RootNavigationGraph(navController = rememberNavController())
                } else {
                    NavHost(navController = navController, startDestination = "Onboarding") {
                        composable("Onboarding") {
                            OnboardingScreen(
                                navController = navController,
                                context = this@MainActivity
                            )
                        }
                        composable("Home") {
                            RootNavigationGraph(navController = rememberNavController())
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        composable(route = Graph.HOME) {
            HomeScreen(rememberNavController())
        }
    }
}


object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}

fun logRegToken() {
    // [START log_reg_token]
    Firebase.messaging.getToken().addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            return@addOnCompleteListener
        }
        // Get new FCM registration token
        val token = task.result

        // Log and toast
        val msg = "FCM Registration token: $token"
        Log.d(TAG, msg)
        GlobalScope.launch {
            getAndStoreRegToken()
        }
    }
    // [END log_reg_token]
}

// [START get_store_token]
private suspend fun getAndStoreRegToken(): String {
    val token = Firebase.messaging.token.await()
    // Add token and timestamp to Firestore for this user
    val deviceToken = hashMapOf(
        "token" to token,
        "timestamp" to FieldValue.serverTimestamp(),
    )

    // Get user ID from Firebase Auth or your own server
    Firebase.firestore.collection("fcmTokens").document("myuserid")
        .set(deviceToken).await()
    return token
}

@Composable
fun AppEntryPoint() {
//    AppNavigationGraph()
}

@Composable
fun NewsInShortEntryPoint() {
    NewsInShortTheme {
        val navController = rememberNavController()
        NavGraphSetup(navController = navController)
    }
}