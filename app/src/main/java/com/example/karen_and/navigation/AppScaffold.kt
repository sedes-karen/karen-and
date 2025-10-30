// AppScaffold.kt
package com.example.karen_and.ui.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.karen_and.data.SessionStore
import com.example.karen_and.navigation.AppDrawerContent
import com.example.karen_and.navigation.AppTopBar
import com.example.karen_and.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    title: String,
    currentRoute: String?,
    onNavigateFromDrawer: (String) -> Unit,
    onLogout: () -> Unit,
    snackbarHostState: SnackbarHostState,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    val scope = rememberCoroutineScope()

    // Rutas con app bar/drawer/bottom bar
    val showChrome = currentRoute in appBarRoutes

    val context = LocalContext.current
    val sessionStore = remember {
        val prefs = context.applicationContext.getSharedPreferences("karen_prefs", Context.MODE_PRIVATE)
        SessionStore(prefs)
    }


    if (showChrome) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    AppDrawerContent(
                        selectedRoute = currentRoute,
                        onNavigate = { route ->
                            onNavigateFromDrawer(route)
                            scope.launch { drawerState.close() }
                        },
                        onLogout = {
                            scope.launch { drawerState.close() }
                            onLogout()
                        },
                        sessionStore = sessionStore

                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    AppTopBar(
                        title = title,
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                },
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = { bottomBar?.invoke() },
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->
                content(Modifier.padding(innerPadding))
            }
        }
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
        }
    }
}
