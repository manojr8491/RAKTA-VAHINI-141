package com.example.myapplication.presentation.search

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.model.BloodGroup
import com.example.myapplication.domain.model.Hospital
import com.example.myapplication.domain.model.User
import com.example.myapplication.presentation.components.GlassCard
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDonorScreen(
    onDonorClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val donors by viewModel.donors.collectAsState()
    val hospitals by viewModel.hospitals.collectAsState()
    var selectedGroup by remember { mutableStateOf<BloodGroup?>(null) }
    var radius by remember { mutableFloatStateOf(10f) }
    val context = LocalContext.current

    Scaffold(
        containerColor = PremiumBlack,
        topBar = {
            TopAppBar(
                title = { Text("INDIAN NETWORK", fontWeight = FontWeight.Bold, letterSpacing = 2.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PremiumBlack)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Mock Map with India Background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color(0xFF0F0F0F))
            ) {
                // Placeholder for India Map Image - Using a centered icon/shape since actual image res might not exist
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.LocationOn, 
                        contentDescription = null, 
                        tint = PremiumRed.copy(alpha = 0.2f),
                        modifier = Modifier.size(150.dp)
                    )
                    Text("LIVE INDIA RADAR", color = TextDisabled, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                // Mock Visual Markers (Scattered)
                donors.forEachIndexed { index, donor ->
                    val x = (100 + (index * 210) % 800).dp
                    val y = (40 + (index * 130) % 180).dp
                    MarkerVisual(x, y, donor.bloodGroup?.displayName ?: "O+")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(PremiumBlack)
                    .padding(24.dp)
            ) {
                Text("NEARBY HOSPITALS & DONORS", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BloodGroup.entries.take(4).forEach { group ->
                        FilterChip(
                            selected = selectedGroup == group,
                            onClick = { 
                                selectedGroup = group 
                                viewModel.searchDonors(group, "")
                            },
                            label = { Text(group.displayName) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PremiumRed,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    item {
                        Text("ACTIVE DONORS", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    items(donors) { donor ->
                        DonorItem(
                            donor = donor, 
                            onCallClick = { 
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${donor.phoneNumber}"))
                                context.startActivity(intent)
                            },
                            onClick = { onDonorClick(donor.uid) }
                        )
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("STRATEGIC HOSPITALS", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    items(hospitals) { hospital ->
                        HospitalItem(hospital = hospital)
                    }
                    
                    item { Spacer(modifier = Modifier.height(120.dp)) }
                }
            }
        }
    }
}

@Composable
fun MarkerVisual(x: androidx.compose.ui.unit.Dp, y: androidx.compose.ui.unit.Dp, label: String) {
    Box(modifier = Modifier.offset(x = x, y = y)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color = PremiumRed,
                shape = CircleShape,
                modifier = Modifier.size(10.dp).shadow(8.dp, CircleShape)
            ) {}
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = PremiumBlack.copy(alpha = 0.8f),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.border(1.dp, PremiumRed.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun DonorItem(donor: User, onCallClick: () -> Unit, onClick: () -> Unit) {
    GlassCard(onClick = onClick) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(50.dp), shape = RoundedCornerShape(12.dp), color = GlassRed) {
                Box(contentAlignment = Alignment.Center) {
                    Text(donor.bloodGroup?.displayName ?: "?", color = PremiumRed, fontWeight = FontWeight.Black, fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(donor.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${donor.city} • Verified Donor", color = TextSecondary, fontSize = 12.sp)
            }
            IconButton(
                onClick = onCallClick,
                modifier = Modifier.size(44.dp).clip(CircleShape).background(SuccessGreen.copy(alpha = 0.1f))
            ) {
                Icon(Icons.Default.Call, contentDescription = "Call", tint = SuccessGreen, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun HospitalItem(hospital: Hospital) {
    val context = LocalContext.current
    GlassCard {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = InfoBlue, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(hospital.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text("${hospital.address} • ${hospital.distance}", color = TextSecondary, fontSize = 12.sp)
            }
            IconButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${hospital.phoneNumber}"))
                    context.startActivity(intent)
                }
            ) {
                Icon(Icons.Default.Call, contentDescription = "Call", tint = TextPrimary, modifier = Modifier.size(18.dp))
            }
        }
    }
}
