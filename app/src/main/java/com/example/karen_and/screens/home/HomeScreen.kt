package com.example.karen_and.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.Icons
import kotlinx.coroutines.launch
import com.example.karen_and.R
import com.example.karen_and.screens.AppDrawerContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val purpleColor = Color(0xFF8A2BE2)
    val whiteColor = Color.White

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(whiteColor)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Bienvenido",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_welcome),
                contentDescription = "Bienvenido",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 8.dp),
                color = Color.LightGray.copy(alpha = 0.7f),
                thickness = 1.dp
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Encontraste un problema?",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Reportar error",
                    style = MaterialTheme.typography.bodySmall,
                    color = purpleColor,
                    modifier = Modifier.clickable { /* Lógica para reportar error */ }
                )
            }
        }
    }
}