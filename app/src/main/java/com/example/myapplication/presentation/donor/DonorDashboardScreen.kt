package com.example.myapplication.presentation.donor

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.components.GlassCard
import com.example.myapplication.ui.theme.*

import androidx.compose.material.icons.automirrored.filled.ExitToApp

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonorDashboardScreen(
    onAdminClick: () -> Unit,
    onCaptainClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: DonorViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val isAvailable = user?.isAvailable ?: false
    val statusColor by animateColorAsState(if (isAvailable) SuccessGreen else TextDisabled, label = "")
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            TopAppBar(
                title = { Text("DONOR COMMAND", fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PremiumBlack),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = PremiumRed)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            GlassCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("ACTIVE STATUS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(
                            if (isAvailable) "READY TO SAVE LIVES" else "CURRENTLY OFFLINE",
                            color = statusColor,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                    }
                    Switch(
                        checked = isAvailable,
                        onCheckedChange = { viewModel.toggleAvailability(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = SuccessGreen,
                            uncheckedTrackColor = GlassGray
                        )
                    )
                }
            }

            Text("ELIGIBILITY TRACKER", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                // Circular Progress
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.size(160.dp),
                    color = GlassRed,
                    strokeWidth = 12.dp,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                
                val lastDonationDays = user?.lastDonationDate?.let {
                    (System.currentTimeMillis() - it) / (1000 * 60 * 60 * 24)
                } ?: 0L
                val progress = (lastDonationDays.toFloat() / 90f).coerceIn(0f, 1f)

                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(160.dp),
                    color = PremiumRed,
                    strokeWidth = 12.dp,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("DAYS", color = TextSecondary, fontSize = 12.sp)
                    Text(lastDonationDays.toString(), color = TextPrimary, fontSize = 32.sp, fontWeight = FontWeight.Black)
                    Text("ELAPSED", color = TextSecondary, fontSize = 12.sp)
                }
            }

            GlassCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val eligible = (user?.lastDonationDate?.let { (System.currentTimeMillis() - it) / (1000 * 60 * 60 * 24) } ?: 91) > 90
                    Icon(
                        if (eligible) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (eligible) SuccessGreen else AccentGold
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        if (eligible) "You are eligible to donate today!" else "Wait ${90 - (user?.lastDonationDate?.let { (System.currentTimeMillis() - it) / (1000 * 60 * 60 * 24) } ?: 0L)} more days.",
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Text("YOUR IMPACT", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatWidget("LIVES SAVED", (user?.livesSaved ?: 0).toString(), PremiumRed, Modifier.weight(1f))
                StatWidget("STREAK", "${user?.donationStreak ?: 0} 🔥", AccentGold, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("MANAGEMENT", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = onAdminClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GlassGray),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ADMIN", color = TextPrimary, fontSize = 12.sp)
                }
                Button(
                    onClick = onCaptainClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GlassGray),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("CAPTAIN", color = TextPrimary, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun StatWidget(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier) {
        Text(label, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text(value, color = color, fontSize = 24.sp, fontWeight = FontWeight.Black)
    }
}
