package com.example.myapplication.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun DonationHistoryScreen(onBack: () -> Unit) {
    val history = listOf(
        Triple("Apollo Hospital", "15 May 2026", "COMPLETED"),
        Triple("City Life Clinic", "02 May 2026", "VERIFIED"),
        Triple("Nanavati Hospital", "14 April 2026", "COMPLETED"),
        Triple("Seven Hills Hospital", "28 March 2026", "VERIFIED")
    )

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            TopAppBar(
                title = { Text("DONATION HISTORY", fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
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
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text("PAST CONTRIBUTIONS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            items(history) { item ->
                HistoryItem(
                    hospital = item.first,
                    date = item.second,
                    status = item.third
                )
            }
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
fun HistoryItem(date: String, hospital: String, status: String) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (status == "COMPLETED") SuccessGreen else InfoBlue)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(hospital, color = TextPrimary, fontWeight = FontWeight.Bold)
                Text(date, color = TextSecondary, fontSize = 12.sp)
            }
            Text(
                status,
                color = if (status == "COMPLETED") SuccessGreen else InfoBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
