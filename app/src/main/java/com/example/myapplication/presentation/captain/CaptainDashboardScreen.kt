package com.example.myapplication.presentation.captain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.components.GlassCard
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptainDashboardScreen(onBack: () -> Unit) {
    val tasks = listOf(
        "Verify O+ Sample at St. John's",
        "Coordinate B- Emergency Transit",
        "Inspect Blood Bank Storage - Area 4"
    )

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            TopAppBar(
                title = { Text("CAPTAIN HUB", fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PremiumBlack)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text("FIELD OPERATIONS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatWidget("MISSION READY", "4", SuccessGreen, Modifier.weight(1f))
                    StatWidget("IMPACT SCORE", "98", AccentGold, Modifier.weight(1f))
                }
            }
            
            item {
                Text("PENDING ASSIGNMENTS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            
            items(tasks) { task ->
                CaptainTaskCard(task)
            }
            
            item {
                Text("NEARBY EMERGENCY OPS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            item {
                GlassCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = EmergencyRed)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("SOS - Bangalore Central", color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text("5 Saviours En Route • Coordination Active", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
fun CaptainTaskCard(title: String) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Verified, contentDescription = null, tint = InfoBlue, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Deadline: Today 4:00 PM", color = TextSecondary, fontSize = 12.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = GlassGray),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text("GO", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
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
