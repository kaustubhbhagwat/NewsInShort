package com.example.newsinshort.ui.screens.home_screen

import android.media.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Badge
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsinshort.R
import com.example.newsinshort.ui.navigation.NavGraphSetup
import com.example.newsinshort.ui.screens.news_screen.NewsScreenViewModel
import com.example.newsinshort.utils.BottomBarScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    newsScreenViewModel: NewsScreenViewModel = hiltViewModel()
) {

    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .consumeWindowInsets(scaffoldPadding)
                .systemBarsPadding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            HomeNavGraph(navController = navController)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(navController: NavHostController) {

    val items = listOf(
        BottomNavigationItem(
            title = "Breaking News",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_breaking_news),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.ic_breaking_news),
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Saved",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_favorite),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.ic_favorite),
            hasNews = false
        )
//        ,
//        BottomNavigationItem(
//            title = "Search",
//            selectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
//            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
//            hasNews = false
//        ),
    )

    val screens =
        listOf(BottomBarScreen.BreakingNews, BottomBarScreen.SavedNews, BottomBarScreen.SearchNews)


    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screens[index].route } == true,
                    onClick = {
                        navController.navigate(screens[index].route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    label = { Text(text = item.title) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge { Text(text = item.badgeCount.toString()) }
                                } else if (item.hasNews) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unSelectedIcon,
                                contentDescription = item.title
                            )
                        }

                    },
                )
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

