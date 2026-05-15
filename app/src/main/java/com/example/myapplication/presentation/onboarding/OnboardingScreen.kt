package com.example.myapplication.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.components.PremiumButton
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val color: Color
)

val onboardingPages = listOf(
    OnboardingPage(
        "AI MATCHING",
        "Our advanced neural network finds the perfect donor for your emergency in seconds.",
        PremiumRed
    ),
    OnboardingPage(
        "REAL-TIME TRACKING",
        "Monitor live donor movement and coordination on a cinematic map interface.",
        InfoBlue
    ),
    OnboardingPage(
        "SAVE LIVES",
        "Join a global ecosystem of heroes and make an emotional impact every single day.",
        SuccessGreen
    )
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PremiumBlack)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val page = onboardingPages[pageIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Animated Hero Visual Placeholder
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(CircleShape)
                        .background(page.color.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.size(200.dp),
                        shape = CircleShape,
                        color = page.color.copy(alpha = 0.2f)
                    ) {
                         // Lottie would go here
                    }
                }
                
                Spacer(modifier = Modifier.height(60.dp))
                
                Text(
                    text = page.title,
                    color = page.color,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = page.description,
                    color = TextSecondary,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
        }

        // Bottom Controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(onboardingPages.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (isSelected) 24.dp else 8.dp, 8.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) PremiumRed else GlassGray)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            PremiumButton(
                text = if (pagerState.currentPage == onboardingPages.size - 1) "GET STARTED" else "NEXT",
                onClick = {
                    if (pagerState.currentPage < onboardingPages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinish()
                    }
                }
            )
        }
    }
}
