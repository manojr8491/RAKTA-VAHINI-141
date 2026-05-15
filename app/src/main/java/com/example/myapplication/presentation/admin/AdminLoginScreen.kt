package com.example.myapplication.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.components.PremiumButton
import com.example.myapplication.ui.theme.*

@Composable
fun AdminLoginScreen(
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PremiumBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ADMIN PORTAL",
                color = PremiumRed,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            )
            Text(
                text = "Enter secure command credentials",
                color = TextSecondary,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Admin Email", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = PremiumRed) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PremiumRed,
                    unfocusedBorderColor = GlassGray,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Security Key", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = PremiumRed) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PremiumRed,
                    unfocusedBorderColor = GlassGray,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            PremiumButton(
                text = "AUTHORIZE ACCESS",
                isLoading = isLoading,
                onClick = {
                    isLoading = true
                    // Simulate Auth
                    onLoginSuccess()
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(GlassGray)
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(32.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("SECURE ACCESS ENABLED", color = TextDisabled, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}
