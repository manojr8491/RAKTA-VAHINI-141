package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object AdminLogin : Screen("admin_login")
    
    // Bottom Nav Screens
    object Home : Screen("home", "Home", Icons.Default.Home)
    object SearchDonor : Screen("search_donor", "Search", Icons.Default.Search)
    object Emergency : Screen("emergency", "SOS", Icons.Default.Warning)
    object Donations : Screen("donations", "History", Icons.Default.DateRange)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)

    // Other Screens
    object DonorProfile : Screen("donor_profile/{donorId}") {
        fun createRoute(donorId: String) = "donor_profile/$donorId"
    }
    object EmergencyRequest : Screen("emergency_request")
    object AdminDashboard : Screen("admin_dashboard")
    object CaptainDashboard : Screen("captain_dashboard")
    object DonorDashboard : Screen("donor_dashboard")
}
