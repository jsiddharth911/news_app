package com.example.news.presentation.navigation

import com.example.news.R

sealed class BottomNavBar(
    val tittle: String,
    val icon: Int = R.drawable.home,
    val route: String
) {
    object Home: BottomNavBar(tittle = "home", icon = R.drawable.home, route = BottomRoutes.HOME.routes)
    object Business: BottomNavBar(tittle = "business", icon = R.drawable.business, route = BottomRoutes.BUSINESS.routes)
    object Sports: BottomNavBar(tittle = "sports", icon = R.drawable.sports, route = BottomRoutes.SPORTS.routes)
    object Science: BottomNavBar(tittle = "science", icon = R.drawable.science, route = BottomRoutes.SCIENCE.routes)
    object Health: BottomNavBar(tittle = "health",  icon = R.drawable.health, route = BottomRoutes.HEALTH.routes)
}

enum class BottomRoutes(val routes: String) {
    HOME("home"),
    BUSINESS("business"),
    SPORTS("sports"),
    SCIENCE("science"),
    HEALTH("health")
}
