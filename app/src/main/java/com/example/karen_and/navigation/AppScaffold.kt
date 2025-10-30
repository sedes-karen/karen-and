// AppScaffold.kt
package com.example.karen_and.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.karen_and.navigation.AppDrawerContent
import com.example.karen_and.navigation.AppTopBar
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    title: String,
    currentRoute: String?,
    onNavigateFromDrawer: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    val scope = rememberCoroutineScope()

    // Rutas con app bar/drawer/bottom bar
    val showChrome = currentRoute in appBarRoutes

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
                        }
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
                bottomBar = { bottomBar?.invoke() }
            ) { innerPadding ->
                content(Modifier.padding(innerPadding))
            }
        }
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
        }
    }
}
