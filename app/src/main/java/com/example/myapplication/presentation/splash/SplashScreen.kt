package com.example.myapplication.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.delay

import androidx.compose.animation.core.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun SplashScreen(onNext: () -> Unit) {
    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        delay(1500)
        onNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PremiumBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "RAKTA-VAHINI",
                color = PremiumRed,
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp,
                modifier = Modifier
                    .scale(scale.value)
                    .graphicsLayer(alpha = alpha.value)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "EVERY DROP COUNTS",
                color = TextSecondary,
                fontSize = 12.sp,
                letterSpacing = 6.sp,
                modifier = Modifier.graphicsLayer(alpha = alpha.value)
            )
        }
    }
}
