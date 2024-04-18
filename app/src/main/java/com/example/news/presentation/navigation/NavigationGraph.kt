package com.example.news.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.news.presentation.navScreens.BusinessScreen
import com.example.news.presentation.navScreens.HomeScreen
import com.example.news.presentation.navScreens.ScienceScreen
import com.example.news.presentation.navScreens.SportsScreen

@Composable
fun NavigationGraph(navHostController: NavHostController, context: Context) {
    NavHost(navController = navHostController, startDestination = BottomRoutes.HOME.routes) {
        composable(route = BottomRoutes.HOME.routes) {
            HomeScreen(context = context)
        }

        composable(route = BottomRoutes.BUSINESS.routes) {
            BusinessScreen()
        }

        composable(route = BottomRoutes.SPORTS.routes) {
            SportsScreen()
        }

        composable(route = BottomRoutes.SCIENCE.routes) {
            ScienceScreen()
        }

        composable(route = BottomRoutes.HEALTH.routes) {
            SportsScreen()
        }
    }
}