package com.example.myapplication.presentation.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.components.GlassCard
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonorProfileScreen(
    donorId: String,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            TopAppBar(
                title = { Text("HERO PROFILE", fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PremiumBlack),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ProfileHeroSection()
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    AchievementBadge("ELITE", Icons.Default.Star, AccentGold, Modifier.weight(1f))
                    AchievementBadge("SAVIOR", Icons.Default.Favorite, PremiumRed, Modifier.weight(1f))
                }
            }

            item {
                GlassCard {
                    Text("DONATION STATS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileStatRow("Total Lives Saved", "12", SuccessGreen)
                    ProfileStatRow("Donation Streak", "4 Months", InfoBlue)
                    ProfileStatRow("Last Donated", "12 Oct 2024", TextPrimary)
                }
            }

            item {
                Text("BIO", modifier = Modifier.fillMaxWidth(), color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Dedicated blood donor since 2020. I believe every drop can bring back a smile. Ready to help Mumbai whenever needed.",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            }

            item {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PremiumRed),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("INITIATE SECURE CALL", fontWeight = FontWeight.Bold)
                }
            }
            
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
fun ProfileHeroSection() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            // Animated Outer Ring
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .graphicsLayer(rotationZ = rotation)
                    .border(
                        width = 4.dp,
                        brush = Brush.sweepGradient(
                            listOf(PremiumRed, Transparent, PremiumRed)
                        ),
                        shape = CircleShape
                    )
            )
            
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(120.dp).border(2.dp, PremiumBlack, CircleShape),
                    shape = CircleShape,
                    color = SurfaceBlack
                ) {
                    // Placeholder for real image
                    Box(contentAlignment = Alignment.Center) {
                        Text("AS", color = TextPrimary, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SuccessGreen)
                        .border(2.dp, PremiumBlack, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Amit Sharma", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Black)
        Text("O Positive Donor", color = PremiumRed, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Text("Mumbai, Maharashtra", color = TextSecondary, fontSize = 12.sp)
    }
}

@Composable
fun AchievementBadge(label: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, color = color, fontSize = 10.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun ProfileStatRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 14.sp)
        Text(value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
