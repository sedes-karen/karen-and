package com.example.karen_and.screens.profile

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val sessionStore = remember {
        val appCtx = context.applicationContext
        val prefs = appCtx.getSharedPreferences("karen_prefs", Context.MODE_PRIVATE)
        com.example.karen_and.data.SessionStore(prefs)
    }

    val name = sessionStore.getUserName().orEmpty()
    val lastname = sessionStore.getUserLastname().orEmpty()

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Text("Hola $name $lastname", style = MaterialTheme.typography.titleLarge)
    }
}