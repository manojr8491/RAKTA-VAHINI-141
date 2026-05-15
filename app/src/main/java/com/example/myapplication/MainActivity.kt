package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.NavGraph
import com.example.myapplication.ui.theme.PremiumBlack
import com.example.myapplication.ui.theme.RaktaVahiniTheme
import dagger.hilt.android.AndroidEntryPoint

import com.example.myapplication.navigation.RootNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("RAKTA", "MainActivity Started")
        enableEdgeToEdge()
        setContent {
            RaktaVahiniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PremiumBlack
                ) {
                    val navController = rememberNavController()
                    RootNavGraph(navController = navController)
                }
            }
        }
    }
}
