package com.example.newsinshort.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.newsinshort.NewsApplication.Companion.TAG
import com.example.newsinshort.R
import com.example.newsinshort.notifications.ChatScreen
import com.example.newsinshort.notifications.ChatViewModel
import com.example.newsinshort.notifications.EnterTokenDialog
//import com.example.newsinshort.ui.navigation.AppNavigationGraph
import com.example.newsinshort.ui.navigation.NavGraphSetup
import com.example.newsinshort.ui.theme.NewsInShortTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
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
                NavGraphSetup(navController = navController)
//                AppEntryPoint()

                // FCM code
//                val state = viewModel.state
//                if (state.isEnteringToken) {
//                    EnterTokenDialog(
//                        token = state.remoteToken, onTokenChange = viewModel::onRemoteTokenChanged,
//                        onSubmit = viewModel::onSubmitToken
//                    )
//                } else {
//                    ChatScreen(
//                        messageText = state.messageText,
//                        onMessageSend = {
//                            viewModel.sendMessage(isBroadcast = false)
//                        },
//                        onMessageBroadcast = {
//                            viewModel.sendMessage(isBroadcast = true)
//                        },
//                        onMessageChange = viewModel::onMessageChange
//                    )
//                }
            }
        }
    }
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