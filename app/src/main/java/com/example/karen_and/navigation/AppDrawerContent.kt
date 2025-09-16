package com.example.karen_and.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.karen_and.R

@Composable
fun AppDrawerContent(
    selectedRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        Routes.HOME to R.string.home,
        Routes.CLASSES to R.string.classes ,
        Routes.PROFILE to R.string.profile
    )

    Column {
        items.forEach { (route, label) ->
            NavigationDrawerItem(
                label = { Text(text = stringResource(label)) },
                selected = selectedRoute == route,
                onClick = { onNavigate(route) },
                modifier = modifier,
                colors = NavigationDrawerItemDefaults.colors()

            )
        }
    }

}