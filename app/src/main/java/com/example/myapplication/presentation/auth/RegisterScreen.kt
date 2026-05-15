package com.example.myapplication.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.components.PremiumButton
import com.example.myapplication.ui.theme.*

@Composable
fun RegisterScreen(
    onRegistrationSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onRegistrationSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PremiumBlack, DeepRed.copy(alpha = 0.2f), PremiumBlack)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "BECOME A SAVIOUR",
                color = PremiumRed,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Text(
                text = "Join the Rakta-Vahini Network",
                color = TextSecondary,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            AuthTextField(value = name, onValueChange = { name = it }, label = "Full Name")
            Spacer(modifier = Modifier.height(16.dp))
            AuthTextField(value = email, onValueChange = { email = it }, label = "Email Address")
            Spacer(modifier = Modifier.height(16.dp))
            AuthTextField(value = bloodGroup, onValueChange = { bloodGroup = it }, label = "Blood Group (e.g. O+)")
            Spacer(modifier = Modifier.height(16.dp))
            AuthTextField(value = location, onValueChange = { location = it }, label = "Current City")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            PremiumButton(
                text = "REGISTER NOW",
                isLoading = isLoading,
                onClick = { 
                    if (name.isNotBlank() && bloodGroup.isNotBlank()) {
                        viewModel.register(name, email, bloodGroup, location)
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            TextButton(onClick = onBackToLogin) {
                Text("ALREADY REGISTERED? LOG IN", color = TextSecondary, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun AuthTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSecondary) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PremiumRed,
            unfocusedBorderColor = GlassGray,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
        ),
        shape = RoundedCornerShape(16.dp)
    )
}
