package com.ex.linko.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ex.linko.R

sealed class BottomNavScreen(val route: String, val label: String, val icon: Int) {
    object Home : BottomNavScreen("home", "Ana Ekran", R.drawable.ic_home)
    object Search : BottomNavScreen("search", "Arama", R.drawable.ic_search)
    object Activities : BottomNavScreen("activities", "Hareketler", R.drawable.ic_activities)
    object Profile : BottomNavScreen("profile", "Profil", R.drawable.ic_profile)
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.Home.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(BottomNavScreen.Home.route) { PlaceholderScreen("Ana Ekran") }
            composable(BottomNavScreen.Search.route) { PlaceholderScreen("Arama") }
            composable(BottomNavScreen.Activities.route) { PlaceholderScreen("Hareketler") }
            composable(BottomNavScreen.Profile.route) { PlaceholderScreen("Profil") }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Activities,
        BottomNavScreen.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) {
                    // Aynı route’a tıklanınca tekrar yüklemesin
                    launchSingleTop = true
                } }
            )
        }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = name, style = MaterialTheme.typography.headlineMedium)
    }
}
