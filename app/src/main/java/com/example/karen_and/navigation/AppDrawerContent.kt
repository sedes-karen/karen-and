package com.example.karen_and.navigation

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.karen_and.R
import com.example.karen_and.data.SessionStore
import com.example.karen_and.models.UserType
import com.example.karen_and.ui.components.LogoutButton

@Composable
fun AppDrawerContent(
    selectedRoute: String?,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    sessionStore: SessionStore

) {
    val role = remember {
        sessionStore.getUserType()
    }

    val baseItems = listOf(
        Routes.HOME to R.string.home,
        Routes.CLASSES to R.string.classes ,
        Routes.PROFILE to R.string.profile,
    )

    val items = if (role == UserType.TEACHER) {
        baseItems + (Routes.ACCEPT_STUDENTS to R.string.accept_students)
    } else {
        baseItems
    }

    Column(
        modifier = modifier.fillMaxHeight().padding(horizontal = 16.dp) ) {
        items.forEach { (route, label) ->
            NavigationDrawerItem(
                label = { Text(text = stringResource(label)) },
                selected = selectedRoute == route,
                onClick = { onNavigate(route) },
                modifier = modifier,
                colors = NavigationDrawerItemDefaults.colors()

            )
        }

        Spacer(modifier = Modifier.weight(1f))
        LogoutButton(
            onLogout
        )
    }

}