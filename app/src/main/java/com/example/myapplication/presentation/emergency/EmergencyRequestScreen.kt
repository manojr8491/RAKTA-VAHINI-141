package com.example.myapplication.presentation.emergency

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.myapplication.domain.model.BloodGroup
import com.example.myapplication.domain.model.EmergencyLevel
import com.example.myapplication.presentation.components.GlassCard
import com.example.myapplication.presentation.components.PremiumButton
import com.example.myapplication.ui.theme.*

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyRequestScreen(
    onBack: () -> Unit,
    viewModel: EmergencyViewModel = hiltViewModel()
) {
    var hospitalName by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf<BloodGroup?>(null) }
    var emergencyLevel by remember { mutableStateOf(EmergencyLevel.NORMAL) }
    
    val isLoading by viewModel.isLoading.collectAsState()
    val sosResult by viewModel.sosResult.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    // Fallback animation if lottie is missing
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    
    LaunchedEffect(sosResult) {
        if (sosResult?.isSuccess == true) {
            onBack()
        }
    }

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            TopAppBar(
                title = { Text("SOS REQUEST", fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PremiumBlack,
                    titleContentColor = EmergencyRed
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = TextPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pulsing SOS Visual
            Box(contentAlignment = Alignment.Center, modifier = Modifier.height(150.dp)) {
                Box(
                    modifier = Modifier
                        .size(100.dp * pulseScale)
                        .clip(CircleShape)
                        .background(EmergencyRed.copy(alpha = 0.2f))
                )
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    color = EmergencyRed
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                }
            }

            Text(
                "SYSTEMS READY",
                color = SuccessGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            GlassCard {
                Text("HOSPITAL DETAILS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = hospitalName,
                    onValueChange = { hospitalName = it },
                    placeholder = { Text("Enter Hospital Name", color = TextDisabled) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PremiumRed,
                        unfocusedBorderColor = GlassGray,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
            
            GlassCard {
                Text("BLOOD GROUP", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BloodGroup.entries.take(4).forEach { group ->
                        FilterChip(
                            selected = selectedGroup == group,
                            onClick = { selectedGroup = group },
                            label = { Text(group.displayName) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PremiumRed,
                                selectedLabelColor = Color.White,
                                labelColor = TextSecondary
                            )
                        )
                    }
                }
            }

            GlassCard {
                Text("EMERGENCY LEVEL", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                PremiumSegmentedButton(emergencyLevel, onLevelChange = { emergencyLevel = it })
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            PremiumButton(
                text = "ACTIVATE SOS",
                isLoading = isLoading,
                onClick = { 
                    if (hospitalName.isNotBlank() && selectedGroup != null) {
                        com.example.myapplication.utils.NotificationUtils.showEmergencyNotification(
                            context, hospitalName, selectedGroup!!.displayName
                        )
                        selectedGroup?.let { 
                            viewModel.triggerSOS(19.0760, 72.8777, it.name) 
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun PremiumSegmentedButton(current: EmergencyLevel, onLevelChange: (EmergencyLevel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(GlassGray)
    ) {
        EmergencyLevel.entries.forEach { level ->
            val isSelected = current == level
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) EmergencyRed else Color.Transparent)
                    .clickable { onLevelChange(level) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    level.name,
                    color = if (isSelected) Color.White else TextSecondary,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
        }
    }
}
