package com.example.karen_and

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.karen_and.navigation.AppNavGraph
import com.example.karen_and.navigation.Routes
import com.example.karen_and.network.RetrofitClient
import com.example.karen_and.ui.components.AppBottomBar
import com.example.karen_and.ui.components.AppScaffold
import com.example.karen_and.ui.theme.KarenandTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.init(applicationContext)

        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            val currentRoute = navController.currentBackStackEntryAsState().value
                ?.destination?.route

            val title = when (currentRoute) {
                Routes.HOME -> stringResource(R.string.home)
                Routes.PROFILE -> stringResource(R.string.profile)
                else -> stringResource(R.string.app_name)
            }

            KarenandTheme {
                AppScaffold(
                    title = title,
                    currentRoute = currentRoute,
                    onNavigateFromDrawer = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onLogout = {
                        // 1) limpiar token
                        val prefs = applicationContext.applicationContext
                            .getSharedPreferences("karen_prefs", MODE_PRIVATE)
                        prefs.edit().remove("auth_token").apply()

                        // 2) ir a LOGIN limpiando back stack
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0) { inclusive = true } // limpia toda la pila
                            launchSingleTop = true
                        }

                        // opcional: feedback
                        scope.launch { snackbarHostState.showSnackbar("Sesión cerrada") }
                    },
                    snackbarHostState = snackbarHostState,
                    bottomBar = {
                        // Se muestra solo si currentRoute ∈ appBarRoutes
                        AppBottomBar(navController)
                    }
                ) { paddingModifier ->
                    AppNavGraph(
                        navController = navController,
                        showSnackbar = { msg ->
                            scope.launch { snackbarHostState.showSnackbar(msg) }
                        },
                        modifier = paddingModifier
                    )
                }


            }
        }
    }
}