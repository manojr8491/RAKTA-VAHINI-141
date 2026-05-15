package com.example.myapplication.presentation.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.components.GlassCard
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.delay

import androidx.compose.foundation.lazy.LazyRow

import com.example.myapplication.domain.model.BloodRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEmergencyClick: () -> Unit,
    onSearchClick: () -> Unit,
    viewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val activeRequests by viewModel.activeRequests.collectAsState()
    val nearbyDonors by viewModel.nearbyDonors.collectAsState()

    val greeting = remember {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        when (hour) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "RAKTA-VAHINI",
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        color = PremiumRed
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PremiumBlack
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = TextPrimary)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEmergencyClick,
                containerColor = EmergencyRed,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(72.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Emergency", modifier = Modifier.size(36.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                LiveTicker(activeRequests)
            }

            item {
                Text(
                    "$greeting, ${user?.name ?: "Saviour"}!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    "Ready to save a life today?",
                    color = TextSecondary,
                    fontSize = 16.sp
                )
            }

            item {
                EmergencyAIWidget(onClick = onEmergencyClick)
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Services", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("View All", color = PremiumRed, fontSize = 14.sp)
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PremiumServiceCard(
                        title = "Find Donor",
                        subtitle = "Nearby Matching",
                        icon = Icons.Default.Search,
                        color = InfoBlue,
                        modifier = Modifier.weight(1f),
                        onClick = onSearchClick
                    )
                    PremiumServiceCard(
                        title = "Hospitals",
                        subtitle = "Blood Banks",
                        icon = Icons.Default.LocationOn,
                        color = SuccessGreen,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                }
            }

            item {
                Text("Network Activity", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedBloodGraph()
            }

            if (nearbyDonors.isNotEmpty()) {
                item {
                    Text("Nearby Donors", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(nearbyDonors) { donor ->
                            RecommendationCard(
                                name = donor.name,
                                group = donor.bloodGroup.toString(),
                                dist = "Available",
                                color = InfoBlue
                            )
                        }
                    }
                }
            }

            item {
                Text("Recent Requests", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            if (activeRequests.isEmpty()) {
                item {
                    Text("No active requests in your area.", color = TextSecondary, fontSize = 14.sp)
                }
            } else {
                items(activeRequests) { request ->
                    GlassCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                shape = CircleShape,
                                color = GlassRed
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(request.bloodGroup.toString(), color = PremiumRed, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Urgent Request: ${request.hospitalName}", color = TextPrimary, fontWeight = FontWeight.Medium)
                                Text("${request.unitsNeeded} units needed", color = TextSecondary, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
fun LiveTicker(activeRequests: List<BloodRequest>) {
    val tickerText = if (activeRequests.isEmpty()) {
        "RAKTA-VAHINI: 4,000+ Donors joined the mission. Join us now! • Secure Life Network Active"
    } else {
        activeRequests.joinToString(" • ") { 
            "URGENT: ${it.bloodGroup.displayName} needed at ${it.hospitalName}"
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val xOffset by infiniteTransition.animateFloat(
        initialValue = 1000f,
        targetValue = -1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GlassRed.copy(alpha = 0.1f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.offset(x = xOffset.toInt().dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Info, contentDescription = null, tint = PremiumRed, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                tickerText,
                color = PremiumRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun EmergencyAIWidget(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(EmergencyRed, DeepRed)
                )
            )
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Badge(containerColor = Color.White.copy(alpha = 0.2f)) {
                Text("AI POWERED", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Need Blood Urgently?",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                "Our AI will find the fastest donor matching your group.",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ACTIVATE EMERGENCY SOS", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
            }
        }
    }
}

@Composable
fun PremiumServiceCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = modifier.height(160.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(subtitle, color = TextSecondary, fontSize = 12.sp)
    }
}

@Composable
fun AnimatedBloodGraph() {
    GlassCard(modifier = Modifier.fillMaxWidth().height(180.dp)) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("DAILY DONATIONS", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text("+12% Today", color = SuccessGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            Box(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                // Mocking a line graph with a path
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = androidx.compose.ui.graphics.Path().apply {
                        moveTo(0f, size.height * 0.8f)
                        quadraticTo(size.width * 0.2f, size.height * 0.6f, size.width * 0.4f, size.height * 0.7f)
                        quadraticTo(size.width * 0.6f, size.height * 0.9f, size.width * 0.8f, size.height * 0.3f)
                        lineTo(size.width, size.height * 0.4f)
                    }
                    drawPath(
                        path = path,
                        color = PremiumRed,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx(), cap = androidx.compose.ui.graphics.StrokeCap.Round)
                    )
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEach {
                    Text(it, color = TextDisabled, fontSize = 9.sp)
                }
            }
        }
    }
}

@Composable
fun RecommendationCard(name: String, group: String, dist: String, color: Color) {
    GlassCard(modifier = Modifier.width(160.dp)) {
        Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = color.copy(alpha = 0.2f)) {
            Box(contentAlignment = Alignment.Center) {
                Text(group, color = color, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(dist, color = TextSecondary, fontSize = 12.sp)
    }
}
