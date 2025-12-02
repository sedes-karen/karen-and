package com.example.karen_and.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape // ¡IMPORTANTE PARA LA FORMA!
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karen_and.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.karen_and.data.SessionStore
import com.example.karen_and.models.UserType
import com.example.karen_and.navigation.Routes
import com.example.karen_and.ui.components.LogoutButton

data class DrawerDataClass(
    val icon: ImageVector,
    val text: String,
    val route: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    modifier: Modifier = Modifier,
    selectedRoute: String?,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    sessionStore: SessionStore
) {


    val classes = listOf("Programación I", "Programación II", "Bases de Datos")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Programación II") }
    val whiteColor = Color.White
    val lightGrayColor = Color.LightGray.copy(alpha = 0.8f)

    val chipBackgroundColor = Color.White.copy(alpha = 0.2f)


    val baseDrawerItems = listOf(
        DrawerDataClass(Icons.Default.Home, stringResource(R.string.home), Routes.HOME),
        DrawerDataClass(Icons.Default.Person, stringResource(R.string.my_profile), Routes.PROFILE),
        DrawerDataClass(Icons.AutoMirrored.Filled.List, stringResource(R.string.classes), Routes.CLASSES),
        DrawerDataClass(Icons.AutoMirrored.Filled.Chat, stringResource(R.string.chat), Routes.CHAT),
    )

    val role = remember {
        sessionStore.getUserType()
    }
    
    val drawerItems = if (role == UserType.TEACHER) {
        baseDrawerItems + DrawerDataClass(Icons.Default.CheckBox, stringResource(R.string.accept_students), Routes.ACCEPT_STUDENTS )
    } else {
        baseDrawerItems
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 24.dp)
        ,horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = sessionStore.getUserName() + sessionStore.getUserLastname(), fontSize = 18.sp, color = whiteColor)
                Text(text = sessionStore.getUserEmail() ?: "hola", fontSize = 14.sp, color = lightGrayColor)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = whiteColor.copy(alpha = 0.5f), thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Buscar clase", color = whiteColor, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedText,
                onValueChange = { },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir",
                        tint = whiteColor
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors(

                    unfocusedContainerColor = chipBackgroundColor,
                    focusedContainerColor = chipBackgroundColor,

                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,

                    focusedTrailingIconColor = whiteColor,
                    unfocusedTrailingIconColor = whiteColor,
                    focusedTextColor = whiteColor,
                    unfocusedTextColor = whiteColor,

                    cursorColor = Color.Transparent,

                    ),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(whiteColor)
            ) {
                classes.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption, color = Color.Black) },
                        onClick = {
                            selectedText = selectionOption
                            expanded = false
                            onNavigate(Routes.CLASSES)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = whiteColor.copy(alpha = 0.5f), thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        drawerItems.forEach {
            NavigationDrawerItem(
                label = { Text(it.text) },
                selected = selectedRoute == it.route,
                onClick = { onNavigate(it.route) },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent,
                    unselectedTextColor = whiteColor,
                    selectedContainerColor = Color(0x33FFFFFF),
                    unselectedIconColor = whiteColor,
                    selectedTextColor = whiteColor
                ),
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.text,
                        modifier = Modifier.size(24.dp),
                        tint = whiteColor
                    )
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        LogoutButton(
            onLogout
        )
    }
}