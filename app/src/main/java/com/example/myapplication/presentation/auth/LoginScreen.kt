package com.example.myapplication.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.components.PremiumButton
import com.example.myapplication.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    onAdminLoginClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var phoneNumber by remember { mutableStateOf("") }
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PremiumBlack, DeepRed.copy(alpha = 0.3f), PremiumBlack)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "RAKTA-VAHINI",
                color = PremiumRed,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Text(
                text = "Secure Life Portal",
                color = TextSecondary,
                fontSize = 16.sp,
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Mobile Number", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                prefix = { Text("+91 ", color = TextPrimary) },
                isError = error != null,
                supportingText = { if (error != null) Text(error!!, color = MaterialTheme.colorScheme.error) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PremiumRed,
                    unfocusedBorderColor = GlassGray,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(16.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            PremiumButton(
                text = "CONTINUE",
                isLoading = isLoading,
                onClick = { viewModel.login(phoneNumber) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(onClick = onRegisterClick) {
                Text("NEW USER? REGISTER HERE", color = TextSecondary, fontSize = 12.sp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "By continuing, you agree to our Terms & Privacy Policy",
                color = TextDisabled,
                fontSize = 12.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            TextButton(onClick = onAdminLoginClick) {
                Text("ENTER ADMIN COMMAND CENTER", color = PremiumRed, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
        }
    }
}
