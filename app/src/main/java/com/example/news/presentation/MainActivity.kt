package com.example.news.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news.presentation.navigation.BottomNavBar
import com.example.news.presentation.navigation.NavigationGraph
import com.example.news.presentation.ui.theme.NewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    newsApp(context = this)
                }
            }
        }
    }
}

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun newsApp(context: Context) {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavBar(navController = navController) },
            containerColor = Color.White,
            contentColor = Color.Black,
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationGraph(navHostController = navController, context = context)
            }
        }
    }

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screenList = listOf(
        BottomNavBar.Home,
        BottomNavBar.Business,
        BottomNavBar.Health,
        BottomNavBar.Science,
        BottomNavBar.Sports
    )

    BottomNavigation {
        screenList.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.route == screen.route,
                onClick = {
                    if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route)
                    }
                          },
                icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = "nav icon",
                            modifier = Modifier.size(24.dp)
                        )
                },
                modifier = Modifier.background(color = Color.White),
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.Blue
            )
        }
    }
}