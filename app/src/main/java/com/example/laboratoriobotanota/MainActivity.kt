package com.example.laboratoriobotanota

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.laboratoriobotanota.ui.theme.LaboratorioBotaNotaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaboratorioBotaNotaTheme {
                QRGeneratorScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRGeneratorScreen() {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var qrUrl by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Generador de QR para LinkedIn",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ingresa tu nombre de usuario de LinkedIn para generar tu código QR",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username de LinkedIn") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFF1F1F1),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.text.isNotBlank()) {
                    isLoading = true
                    val linkedInUrl = "https://www.linkedin.com/in/${username.text}"
                    qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=$linkedInUrl"
                    isLoading = false
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Generar QR", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF2196F3),
                modifier = Modifier.size(50.dp)
            )
        } else if (qrUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(qrUrl),
                contentDescription = "QR Code",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}
