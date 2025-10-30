package com.example.karen_and

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.karen_and.navigation.AppNavGraph
import com.example.karen_and.navigation.Routes
import com.example.karen_and.ui.components.AppBottomBar // NOTA: Ya no se usa, pero lo mantengo por si acaso.
import com.example.karen_and.ui.components.AppScaffoldWithDrawer
import com.example.karen_and.ui.components.appBarRoutes
import com.example.karen_and.ui.theme.KarenandTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val backStackEntry = navController.currentBackStackEntryAsState().value
            val currentRoute = backStackEntry?.destination?.route

            KarenandTheme {
                if(currentRoute in appBarRoutes) {
                    AppScaffoldWithDrawer(
                        title = when (currentRoute) {
                            Routes.HOME -> stringResource(R.string.home)
                            Routes.PROFILE -> stringResource(R.string.profile)
                            else -> stringResource(R.string.app_name)
                        },
                        selectedRoute = currentRoute,
                        onNavigateFromDrawer = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        // **¡CAMBIO CLAVE AQUÍ!**
                        // Se elimina el parámetro bottomBar para eliminar la barra de navegación inferior.
                        // bottomBar = { AppBottomBar(navController) },
                        snackbarHostState = snackbarHostState
                    ) {
                        AppNavGraph(
                            navController = navController,
                            showSnackbar = { msg -> scope.launch { snackbarHostState.showSnackbar(msg) } },
                            modifier = it
                        )
                    }
                } else {
                    AppNavGraph(
                        navController = navController,
                        showSnackbar = { msg -> scope.launch { snackbarHostState.showSnackbar(msg) } },
                    )
                }


            }
        }
    }
}