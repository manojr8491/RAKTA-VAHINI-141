package com.example.myapplication.presentation.admin

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.model.*
import com.example.myapplication.presentation.components.GlassCard
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.launch

enum class AdminTab {
    DASHBOARD,
    VERIFICATIONS,
    REPORTS,
    LOGS,
    HOSPITALS,
    BLOOD_BANKS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onBack: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val analytics by viewModel.analytics.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val verifications by viewModel.pendingVerifications.collectAsState()
    val fraudAlerts by viewModel.fraudAlerts.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentTab by remember { mutableStateOf(AdminTab.DASHBOARD) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AdminSideNav(
                selectedTab = currentTab,
                onTabSelected = { 
                    currentTab = it
                    scope.launch { drawerState.close() }
                },
                onClose = {
                    scope.launch { drawerState.close() }
                }
            )
        },
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        Scaffold(
            containerColor = PremiumBlack,
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(currentTab.name.replace("_", " "), fontWeight = FontWeight.Black, letterSpacing = 2.sp, fontSize = 18.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SuccessGreen))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("SYSTEMS ONLINE", color = SuccessGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    },
                    navigationIcon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                            }
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = null, tint = TextPrimary)
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            BadgedBox(badge = { if(fraudAlerts.isNotEmpty()) Badge { Text(fraudAlerts.size.toString()) } }) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = TextPrimary)
                            }
                        }
                        Surface(
                            modifier = Modifier.padding(end = 16.dp).size(32.dp),
                            shape = CircleShape,
                            color = GlassRed
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("SA", color = PremiumRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = PremiumBlack, titleContentColor = TextPrimary)
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (currentTab) {
                    AdminTab.DASHBOARD -> DashboardContent(analytics, fraudAlerts, verifications, logs)
                    AdminTab.VERIFICATIONS -> VerificationsContent(verifications)
                    AdminTab.REPORTS -> ReportsContent()
                    AdminTab.LOGS -> LogsContent(logs)
                    else -> PlaceholderContent(currentTab.name)
                }
            }
        }
    }
}

@Composable
fun DashboardContent(
    analytics: AdminAnalytics,
    fraudAlerts: List<FraudAlert>,
    verifications: List<VerificationRequest>,
    logs: List<SystemLog>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            RealTimeOverview(analytics)
        }

        item {
            SectionHeader("AI FRAUD MONITORING", Icons.Default.Security)
            Spacer(modifier = Modifier.height(16.dp))
            FraudAlertWidget(fraudAlerts)
        }

        item {
            SectionHeader("PENDING VERIFICATIONS", Icons.Default.AccountBox)
            Spacer(modifier = Modifier.height(16.dp))
            VerificationQueue(verifications)
        }

        item {
            SectionHeader("LIVE SYSTEM LOGS", Icons.AutoMirrored.Filled.List)
            Spacer(modifier = Modifier.height(16.dp))
            GlassCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    logs.take(3).forEach { log ->
                        LogEntry(log)
                    }
                }
            }
        }
        
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@Composable
fun VerificationsContent(verifications: List<VerificationRequest>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        items(verifications) { req ->
            VerificationDetailCard(req)
        }
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@Composable
fun LogsContent(logs: List<SystemLog>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        items(logs) { log ->
            GlassCard {
                LogEntry(log)
            }
        }
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@Composable
fun ReportsContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Report Management System", color = TextSecondary)
    }
}

@Composable
fun PlaceholderContent(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("$title module coming soon", color = TextDisabled)
    }
}

@Composable
fun AdminSideNav(
    selectedTab: AdminTab,
    onTabSelected: (AdminTab) -> Unit,
    onClose: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = SurfaceBlack,
        drawerShape = RoundedCornerShape(0.dp)
    ) {
        Column(modifier = Modifier.fillMaxHeight().padding(24.dp)) {
            Text("Rakta-Vahini Admin", color = PremiumRed, fontWeight = FontWeight.Black, fontSize = 20.sp)
            Text("v2.4.0-Enterprise", color = TextDisabled, fontSize = 10.sp)
            
            Spacer(modifier = Modifier.height(48.dp))
            
            NavMenuItem("Dashboard", Icons.Default.Dashboard, selectedTab == AdminTab.DASHBOARD) { onTabSelected(AdminTab.DASHBOARD) }
            NavMenuItem("Verifications", Icons.Default.FactCheck, selectedTab == AdminTab.VERIFICATIONS) { onTabSelected(AdminTab.VERIFICATIONS) }
            NavMenuItem("Reports", Icons.Default.Report, selectedTab == AdminTab.REPORTS) { onTabSelected(AdminTab.REPORTS) }
            NavMenuItem("System Logs", Icons.AutoMirrored.Filled.List, selectedTab == AdminTab.LOGS) { onTabSelected(AdminTab.LOGS) }
            NavMenuItem("Hospitals", Icons.Default.LocationOn, selectedTab == AdminTab.HOSPITALS) { onTabSelected(AdminTab.HOSPITALS) }
            NavMenuItem("Blood Banks", Icons.Default.Business, selectedTab == AdminTab.BLOOD_BANKS) { onTabSelected(AdminTab.BLOOD_BANKS) }
            
            Spacer(modifier = Modifier.weight(1f))
            
            NavMenuItem("Security Settings", Icons.Default.Lock, false) {}
            
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GlassRed),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("CLOSE COMMAND", color = PremiumRed, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun NavMenuItem(label: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) GlassRed else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if (isSelected) PremiumRed else TextSecondary)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, color = if (isSelected) TextPrimary else TextSecondary, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium)
    }
}

@Composable
fun VerificationDetailCard(req: VerificationRequest) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(50.dp), shape = CircleShape, color = GlassRed) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        if (req.entityType == EntityType.USER) Icons.Default.Person else Icons.Default.HomeWork,
                        contentDescription = null,
                        tint = PremiumRed
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(req.entityId, color = TextPrimary, fontWeight = FontWeight.Bold)
                Text(req.entityType.name, color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(if(req.aiRiskScore < 0.1) SuccessGreen else AccentGold))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("AI Risk Score: ${(req.aiRiskScore * 100).toInt()}%", color = TextDisabled, fontSize = 10.sp)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text("APPROVE", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(32.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, EmergencyRed.copy(alpha = 0.5f)),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text("REJECT", color = EmergencyRed, fontSize = 10.sp)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("SUBMITTED DOCUMENTS", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DocThumbnail("ID PROOF")
            DocThumbnail("MED CERT")
            if(req.entityType == EntityType.HOSPITAL) DocThumbnail("LICENSE")
        }
    }
}

@Composable
fun DocThumbnail(label: String) {
    Box(
        modifier = Modifier
            .size(80.dp, 50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GlassGray)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = TextDisabled, fontSize = 8.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RealTimeOverview(analytics: AdminAnalytics) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        StatCard("USERS", analytics.totalUsers.toString(), SuccessGreen, Modifier.weight(1f))
        StatCard("SOS", analytics.activeEmergencies.toString(), EmergencyRed, Modifier.weight(1f))
    }
    Spacer(modifier = Modifier.height(16.dp))
    
    // Mini Chart
    GlassCard(modifier = Modifier.fillMaxWidth().height(150.dp)) {
        Column {
            Text("NETWORK LOAD", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(0.4f, 0.7f, 0.5f, 0.9f, 0.6f, 0.8f, 1f).forEach { height ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(height)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                    colors = listOf(PremiumRed, PremiumRed.copy(alpha = 0.2f))
                                )
                            )
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        StatCard("DONORS", analytics.verifiedDonors.toString(), InfoBlue, Modifier.weight(1f))
        StatCard("SUCCESS", "${(analytics.donationSuccessRate * 100).toInt()}%", AccentGold, Modifier.weight(1f))
    }
}

@Composable
fun StatCard(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color).shadow(4.dp, CircleShape))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Text(value, color = TextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = PremiumRed, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, fontSize = 14.sp)
    }
}

@Composable
fun FraudAlertWidget(alerts: List<FraudAlert>) {
    GlassCard {
        if (alerts.isEmpty()) {
            Text("No critical fraud detected", color = SuccessGreen, fontSize = 12.sp)
        } else {
            alerts.forEach { alert ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = EmergencyRed, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(alert.riskType, color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text(alert.description, color = TextSecondary, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Badge(containerColor = EmergencyRed) {
                        Text("${(alert.riskScore * 100).toInt()}% RISK", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun VerificationQueue(requests: List<VerificationRequest>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        requests.take(2).forEach { req ->
            GlassCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = GlassRed) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                if (req.entityType == EntityType.USER) Icons.Default.Person else Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = PremiumRed
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(req.entityId, color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text(req.entityType.name, color = TextSecondary, fontSize = 12.sp)
                    }
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = PremiumRed),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text("REVIEW", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun LogEntry(log: SystemLog) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            "[${java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.US).format(log.timestamp)}]",
            color = TextDisabled,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(
                    when (log.level) {
                        "EMERGENCY" -> EmergencyRed
                        "WARNING" -> AccentGold
                        else -> InfoBlue
                    }
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(log.message, color = TextPrimary, fontSize = 12.sp)
    }
}

@Composable
fun RegionalHeatmapPlaceholder() {
    GlassCard(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = TextDisabled, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("REAL-TIME HEATMAP LOADING...", color = TextDisabled, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
