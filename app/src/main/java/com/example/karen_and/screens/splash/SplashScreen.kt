package com.example.karen_and.screens.splash

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.karen_and.R
import com.example.karen_and.data.SessionStore
import com.example.karen_and.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val appCtx = context.applicationContext

    LaunchedEffect(Unit) {
        val prefs = appCtx.getSharedPreferences("karen_prefs", Context.MODE_PRIVATE)
        val sessionStore = SessionStore(prefs)
        val token = sessionStore.getToken()

        delay(1000)

        if (token.isNullOrBlank()) {
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.SPLASH) { inclusive = true }
                launchSingleTop = true
            }
        } else {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.SPLASH) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.logo_karen),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp)
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
        )
    }
}
