package com.example.karen_and.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.karen_and.navigation.AppDrawerContent
import com.example.karen_and.navigation.AppTopBar
import kotlinx.coroutines.launch

@Composable
fun AppScaffoldWithDrawer(
    title: String,
    selectedRoute: String?,
    onNavigateFromDrawer: (String) -> Unit,
    bottomBar: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (Modifier) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                AppDrawerContent(
                    selectedRoute = selectedRoute,
                    onNavigate = { route ->
                        onNavigateFromDrawer(route)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if(selectedRoute in appBarRoutes) {
                    AppTopBar(
                        title = title,
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = { bottomBar?.invoke() }
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
        }
    }
}
