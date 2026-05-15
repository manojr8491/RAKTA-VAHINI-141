package com.example.myapplication.presentation.main

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.NavGraph
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.theme.*

@Composable
fun MainContainer(rootNavController: NavHostController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.SearchDonor,
        Screen.Emergency,
        Screen.Donations,
        Screen.Profile
    )

    val showBottomNav = currentDestination?.route in bottomNavItems.map { it.route }

    Scaffold(
        containerColor = PremiumBlack
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Main Content
            Box(modifier = Modifier.fillMaxSize()) {
                NavGraph(
                    navController = bottomNavController,
                    onLogout = {
                        rootNavController.navigate(Screen.Login.route) {
                            popUpTo(0)
                        }
                    }
                )
            }

            // Floating Navigation Bar
            if (showBottomNav) {
                FloatingBottomBar(
                    items = bottomNavItems,
                    currentDestination = currentDestination,
                    onNavigate = { screen ->
                        bottomNavController.navigate(screen.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
fun FloatingBottomBar(
    items: List<Screen>,
    currentDestination: androidx.navigation.NavDestination?,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .shadow(25.dp, RoundedCornerShape(32.dp), ambientColor = Color.Black, spotColor = Color.Black)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xE6121212)) // Deep Glass Black
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(32.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                        .clickable { onNavigate(screen) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = screen.icon!!,
                            contentDescription = screen.title,
                            tint = if (selected) PremiumRed else TextDisabled,
                            modifier = Modifier.size(if (selected) 26.dp else 24.dp)
                        )
                        if (selected) {
                            Text(
                                text = screen.title!!,
                                color = PremiumRed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
