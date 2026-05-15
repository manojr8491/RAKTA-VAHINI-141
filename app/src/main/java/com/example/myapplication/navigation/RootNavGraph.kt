package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.presentation.auth.LoginScreen
import com.example.myapplication.presentation.auth.RegisterScreen
import com.example.myapplication.presentation.main.MainContainer
import com.example.myapplication.presentation.splash.SplashScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

import com.example.myapplication.presentation.admin.AdminDashboardScreen
import com.example.myapplication.presentation.admin.AdminLoginScreen
import com.example.myapplication.presentation.onboarding.OnboardingScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(tween(700)) + slideInHorizontally(tween(700)) { it } },
        exitTransition = { fadeOut(tween(700)) + slideOutHorizontally(tween(700)) { -it } }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNext = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main_container") {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onAdminLoginClick = {
                    navController.navigate(Screen.AdminLogin.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegistrationSuccess = {
                    navController.navigate("main_container") {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.AdminLogin.route) {
            AdminLoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.AdminDashboard.route) {
                        popUpTo(Screen.AdminLogin.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(onBack = { navController.popBackStack() })
        }
        composable("main_container") {
            MainContainer(rootNavController = navController)
        }
    }
}
