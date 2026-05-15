package com.example.myapplication.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.presentation.admin.AdminDashboardScreen
import com.example.myapplication.presentation.captain.CaptainDashboardScreen
import com.example.myapplication.presentation.donor.DonorDashboardScreen
import com.example.myapplication.presentation.emergency.EmergencyRequestScreen
import com.example.myapplication.presentation.history.DonationHistoryScreen
import com.example.myapplication.presentation.home.HomeScreen
import com.example.myapplication.presentation.profile.DonorProfileScreen
import com.example.myapplication.presentation.search.SearchDonorScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { scaleIn(tween(500), 0.9f) + fadeIn(tween(500)) },
        exitTransition = { scaleOut(tween(500), 1.1f) + fadeOut(tween(500)) },
        popEnterTransition = { scaleIn(tween(500), 1.1f) + fadeIn(tween(500)) },
        popExitTransition = { scaleOut(tween(500), 0.9f) + fadeOut(tween(500)) }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onEmergencyClick = { navController.navigate(Screen.EmergencyRequest.route) },
                onSearchClick = { navController.navigate(Screen.SearchDonor.route) }
            )
        }
        composable(Screen.SearchDonor.route) {
            SearchDonorScreen(
                onDonorClick = { donorId ->
                    navController.navigate(Screen.DonorProfile.createRoute(donorId))
                }
            )
        }
        composable(Screen.Emergency.route) {
             EmergencyRequestScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Donations.route) {
            DonationHistoryScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Profile.route) {
            DonorDashboardScreen(
                onAdminClick = { navController.navigate(Screen.AdminDashboard.route) },
                onCaptainClick = { navController.navigate(Screen.CaptainDashboard.route) },
                onLogout = onLogout
            )
        }
        
        composable(Screen.DonorProfile.route) { backStackEntry ->
            val donorId = backStackEntry.arguments?.getString("donorId") ?: ""
            DonorProfileScreen(
                donorId = donorId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.EmergencyRequest.route) {
            EmergencyRequestScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.CaptainDashboard.route) {
            CaptainDashboardScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.DonorDashboard.route) {
            DonorDashboardScreen(
                onAdminClick = { navController.navigate(Screen.AdminDashboard.route) },
                onCaptainClick = { navController.navigate(Screen.CaptainDashboard.route) },
                onLogout = onLogout
            )
        }
    }
}
