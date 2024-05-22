import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.newsinshort.R
import com.example.newsinshort.notifications.ChatViewModel
import com.example.newsinshort.ui.screens.home_screen.HomeScreen
import com.example.newsinshort.ui.screens.news_screen.NewsScreen
import com.example.newsinshort.ui.screens.news_screen.NewsScreenViewModel

@Composable
fun BottomNavigationExample() {

    val screens = listOf("Home", "Saved")
    var selectedScreen by remember { mutableStateOf(screens.first()) }
    val viewModel: NewsScreenViewModel = hiltViewModel()
    val navController = rememberNavController()
    val argKey = "webUrl"


    Scaffold(
        bottomBar = {
            BottomNavigation {
                screens.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(getIconForScreen(screen), contentDescription = screen) },
                        label = { Text(screen , color = Color.White) },
                        selected = screen == selectedScreen,
                        onClick = { selectedScreen = screen },
                        modifier = Modifier.background(Color.Black)
                    )
                }
            }
        },
        content = { padding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(selectedScreen == "Home"){
                    NewsScreen(state = viewModel.state,
                        onEvent = viewModel::onEvent,
                        onReadFullStoryButtonClick = { url ->
                            navController.navigate("article_screen?$argKey=$url")
                        })
                }else if (selectedScreen == "Saved"){


                }
            }
        }
    )
}

@Composable
fun getIconForScreen(screen: String): ImageVector {
    return when (screen) {
        "Home" -> Icons.Default.Home
        "Saved" -> Icons.Default.Favorite
        "Post" -> Icons.Default.Add
//        "Alert" -> Icons.Default.Notifications
//        "Jobs" -> Icons.Default.Done
        else -> Icons.Default.Home
    }
}