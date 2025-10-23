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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ArrowDropDown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToClasses: () -> Unit,
    onNavigateToChat: () -> Unit
) {
    val classes = listOf("Programación I", "Programación II", "Bases de Datos")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Programación II") } // Cambiado a "Programación II" para simular la imagen

    val whiteColor = Color.White
    val lightGrayColor = Color.LightGray.copy(alpha = 0.8f)

    // Nuevo color para la "pastilla" de búsqueda (Gris claro/transparente)
    val chipBackgroundColor = Color.White.copy(alpha = 0.2f)
    val chipTextColor = whiteColor // El texto dentro de la pastilla es blanco
    val chipLabelColor = lightGrayColor // El label es gris claro


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 24.dp)
        ,horizontalAlignment = Alignment.Start
    ) {
        // ... (Sección de perfil sin cambios)
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
                Text(text = "Agustin Gomez", fontSize = 18.sp, color = whiteColor)
                Text(text = "agustin.g2400@gmail.com", fontSize = 14.sp, color = lightGrayColor)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = whiteColor.copy(alpha = 0.5f), thickness = 1.dp)

        // --- APARTADO DE BUSCAR CLASE (Diseño de Pastilla) ---
        Spacer(modifier = Modifier.height(16.dp))

        Text("Buscar clase", color = chipTextColor, fontSize = 14.sp)
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
                        tint = chipTextColor // Ícono blanco dentro de la pastilla
                    )
                },
                shape = RoundedCornerShape(24.dp), // **APLICAMOS LA FORMA DE PASTILLA**
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    // Color de fondo de la pastilla
                    unfocusedContainerColor = chipBackgroundColor,
                    focusedContainerColor = chipBackgroundColor,

                    // Eliminamos los indicadores para una apariencia limpia
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,

                    // Colores de los elementos dentro de la pastilla
                    focusedTrailingIconColor = chipTextColor,
                    unfocusedTrailingIconColor = chipTextColor,
                    focusedTextColor = chipTextColor,
                    unfocusedTextColor = chipTextColor,

                    // Desactivamos el cursor
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
                            onNavigateToClasses()
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = whiteColor.copy(alpha = 0.5f), thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
        // --- FIN DEL APARTADO DE BUSCAR CLASE ---


        // Opciones de navegación del menú (sin cambios)
        DrawerItem(Icons.Default.Home, "Inicio", onNavigateToHome, whiteColor)
        DrawerItem(Icons.Default.Person, "Mi perfil", onNavigateToProfile, whiteColor)
        DrawerItem(Icons.Default.List, "Clases", onNavigateToClasses, whiteColor)
        DrawerItem(Icons.Default.Chat, "Chat", onNavigateToChat, whiteColor)
    }
}

// ... (Resto de las funciones permanecen igual)
@Composable
fun DrawerItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(24.dp), tint = color)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp, color = color)
    }
}