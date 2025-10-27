package com.example.karen_and.screens.classdetails

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.karen_and.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel


@Composable
fun tamanoActual():Dp{
    val dpActual = LocalConfiguration.current.screenWidthDp.dp
    return dpActual
}

val montserrat = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

data class Subject(
    val id: Int,
    val name: String,
    val userId: Int,
    val classes: List<Clase>
)

data class Clase(
    val id: Int,
    val subjectId: Int,
    val date: String,
    val status: ClassStatus,
    val contents: List<Content>
)

data class ClassStatus(
    val id: Int,
    val name: String
)

data class Content(
    val id: Int,
    val classId: Int,
    val content: String
)

val materia = Subject(
    id = 1,
    name = "Programación III",
    userId = 12,
    classes = listOf(
        Clase(
            id = 104,
            subjectId = 1,
            date = "30/10/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(1, 101, "Entorno de desarrollo y herramientas avanzadas: configuración del IDE, depuración de código y gestión de dependencias en proyectos de gran escala. Se abordan estrategias de optimización del flujo de trabajo y control de versiones colaborativo.")
            )
        ),
        Clase(
            id = 103,
            subjectId = 1,
            date = "23/10/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(2, 102, "Operadores básicos y expresiones complejas: uso combinado de operadores lógicos, aritméticos y relacionales en estructuras condicionales. Ejemplos prácticos de evaluación de expresiones compuestas y errores comunes al anidar condiciones.")
            )
        ),
        Clase(
            id = 102,
            subjectId = 1,
            date = "16/10/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(3, 103, "Condicionales y bucles: estructuras de control avanzadas, iteración con rangos, colecciones y flujos de datos. Se analiza cómo optimizar los ciclos y aplicar patrones de diseño para mejorar la eficiencia del código.")
            )
        ),
        Clase(
            id = 101,
            subjectId = 1,
            date = "15/10/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(4, 104, "Funciones: definición, parámetros, retorno de valores, sobrecarga y modularización del código. Se enfatiza la reutilización de funciones y la separación lógica del programa para mantener escalabilidad y legibilidad.")
            )
        ),
        Clase(
            id = 100,
            subjectId = 1,
            date = "8/10/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(5, 105, "Introducción a la programación estructurada: análisis del flujo de ejecución, variables, tipos de datos y operadores. Se presentan buenas prácticas iniciales para el desarrollo limpio y organizado en proyectos pequeños.")
            )
        ),
        Clase(
            id = 99,
            subjectId = 1,
            date = "2/10/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(6, 106, "Conceptos fundamentales del pensamiento algorítmico: definición de algoritmos, pseudocódigo y diagramas de flujo. Ejercicios orientados a la resolución lógica y sistemática de problemas computacionales.")
            )
        ),
        Clase(
            id = 98,
            subjectId = 1,
            date = "25/09/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(7, 107, "Estructura básica de un programa y sintaxis del lenguaje: reglas de declaración, comentarios, y estilo de código. Enfatiza la importancia de la legibilidad y consistencia en equipos de trabajo colaborativos.")
            )
        ),
        Clase(
            id = 97,
            subjectId = 1,
            date = "18/09/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(8, 108, "Instalación del entorno de trabajo y primeros pasos en la codificación. Configuración de dependencias, exploración de la consola y creación del primer proyecto funcional paso a paso.")
            )
        ),
        Clase(
            id = 96,
            subjectId = 1,
            date = "11/09/2025",
            status = ClassStatus(1, "Creado"),
            contents = listOf(
                Content(9, 109, "Presentación del curso: introducción a los objetivos, metodología y herramientas a utilizar. Explicación del cronograma de contenidos, dinámica de evaluación y expectativas de participación.")
            )
        )


    )
)



object ResponsiveText {
    @Composable
    fun h5() = (LocalConfiguration.current.screenWidthDp * 0.045).sp

    @Composable
    fun h4() = (LocalConfiguration.current.screenWidthDp * 0.05).sp

    @Composable
    fun h3() = (LocalConfiguration.current.screenWidthDp * 0.06).sp

    @Composable
    fun h2() = (LocalConfiguration.current.screenWidthDp * 0.07).sp

    @Composable
    fun h1() = (LocalConfiguration.current.screenWidthDp * 0.08).sp

}

class DetailsViewModel : ViewModel() {
    var showDialog by mutableStateOf(false)
        private set

    var selectedClass by mutableStateOf<Clase?>(null)
        private set

    fun openDialog(clase: Clase) {
        selectedClass = clase
        showDialog = true
    }

    fun closeDialog() {
        showDialog = false
        selectedClass = null
    }
}




@Composable
fun ClassDetails( //////////FUNCIÓN PRINCIPAL DE TODA LA PAGINA ------------------------------------
    onNavigateHome: () -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: DetailsViewModel = viewModel()
) {
    val viewModel: DetailsViewModel = viewModel()
    /*
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UIEvents.ShowSnackbar -> showSnackbar(event.message)
                else -> {}
            }
        }
    }
    */// necesitaba el loginViewModel


    Column (/// TODA LA PANTALLA ------------------------------------------------------
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar()
        Titulo()
        ContentList()
        BottomBar()

    }
    // --- Overlay / Alerta ---
    if (viewModel.showDialog && viewModel.selectedClass != null) {
        val claseSeleccionada = viewModel.selectedClass!!
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            title = { Text( //// FECHA
                text= claseSeleccionada.date,
                fontFamily = montserrat,
                fontSize = ResponsiveText.h1(),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(vertical=tamanoActual()*0.05f)

            ) },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical=tamanoActual()*0.05f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { viewModel.closeDialog() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9950FF), // 👈 color de fondo
                            contentColor = Color.White          // 👈 color del texto o ícono
                        ),
                        modifier = Modifier
                            .height(tamanoActual()*0.11f)
                    ) {
                        Text(
                            text = "OK",
                            fontSize = ResponsiveText.h3(),
                            fontFamily = montserrat,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()

                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0)),
            text = {
                Column(
                    modifier = Modifier
                        .heightIn(max = tamanoActual()*0.9f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text= claseSeleccionada.contents[0].content,
                        fontFamily = montserrat,
                        fontSize = ResponsiveText.h3(),
                        lineHeight = 30.sp

                    )
                }
            }

        )
    }
}


@Composable
fun TopBar(){
    Row( /// BARRA SUPERIOR -----------------------------------
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f)
            .clip(
                RoundedCornerShape(
                    bottomEnd = tamanoActual() * 0.09f
                )
            )
            .background(color = Color(0xFF9950FF))

    ){
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Abrir Menú",
            tint = Color(0xFFD9D9D9),
            modifier = Modifier
                .size(tamanoActual() * 0.2f)
                .padding(start = tamanoActual() * 0.05f, top = tamanoActual() * 0.06f)
        )
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(tamanoActual() * 0.17f)
                .padding(end = tamanoActual() * 0.05f, top = tamanoActual() * 0.06f)
        )


    }
}

@Composable
fun Titulo(){
    Row( /////////  FILA CON TITULO DE LA MATERIA
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08f)
    ){
        Text(
            text = materia.name,
            fontSize = ResponsiveText.h2(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = montserrat,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
fun ContentList(viewModel: DetailsViewModel = viewModel()) {
    Row(
        modifier = Modifier.fillMaxHeight(0.87f)
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = tamanoActual() * 0.03f),
        ) {
            items(materia.classes) { clase ->
                AddContent(clase, viewModel)
            }
        }
    }
}

@Composable
fun AddContent(clase: Clase, viewModel: DetailsViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=tamanoActual() * 0.07f, vertical =  tamanoActual()*0.03f)
            .clickable { viewModel.openDialog(clase) }, // ✅ abre con datos reales
        shape = RoundedCornerShape(tamanoActual() * 0.05f),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.padding(horizontal=tamanoActual() * 0.07f, vertical = tamanoActual() * 0.04f)) {
            Text(
                text = clase.date,
                fontFamily = montserrat,
                fontSize = ResponsiveText.h3()
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Text(
                text = clase.contents[0].content.take(70) + "...",
                fontSize = ResponsiveText.h5(),
                fontFamily = montserrat,
                modifier = Modifier.padding(top = tamanoActual() * 0.015f)
            )
        }
    }
}

@Composable
fun BottomBar(){
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxHeight()
    ){
        Box(Modifier.fillMaxWidth()){
            Box( // BARRA INFERIOR ---------------------------------
                // barra violeta
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = tamanoActual() * 0.09f,
                            topEnd = tamanoActual() * 0.09f
                        )
                    )
                    .background(color = Color(0xFF9950FF))
            ){
                Barra_con_curva(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.83f)
                        .align(Alignment.BottomStart),
                    fillColor = Color.White,
                )

                Row( /// FILA PRINCIPAL DE LOS BOTONES
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = tamanoActual() * 0.06f, vertical=tamanoActual() * 0.03f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // PRIMEROS DOS BOTONES
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(tamanoActual() * 0.08f),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom=tamanoActual()*0.04f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Inicio",
                            tint = Color(0xFFBBBABA),
                            modifier = Modifier.size(tamanoActual() * 0.1f)
                        )
                        Icon(
                            imageVector = Icons.Outlined.Chat,
                            contentDescription = "Chat",
                            tint = Color(0xFFBBBABA),
                            modifier = Modifier.size(tamanoActual() * 0.1f)
                        )
                    }

                    // SEGUNDOS DOS BOTONES
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(tamanoActual() * 0.08f),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom=tamanoActual()*0.04f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Configuración",
                            tint = Color(0xFFBBBABA),
                            modifier = Modifier.size(tamanoActual() * 0.1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Perfil",
                            tint = Color(0xFFBBBABA),
                            modifier = Modifier.size(tamanoActual() * 0.1f)
                        )
                    }
                }

            }

        }

        Box(//// Circulo para las notifications
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(tamanoActual() * 0.16f)
                .align(Alignment.TopCenter)
                .offset(y = -(tamanoActual() * 0.035f))
                .clip(CircleShape)
                .background(color = Color(0xFFE874FF)),

        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Agregar",
                tint = Color.White,
                modifier = Modifier.size(tamanoActual() * 0.11f)
            )

    }

    }


}

@Composable
fun Barra_con_curva(modifier: Modifier = Modifier, fillColor: Color = Color.Gray, ) {
    val widthDp = LocalConfiguration.current.screenWidthDp.dp
    val heightDp = tamanoActual() * 0.2f

    val ancho = with(LocalDensity.current) { widthDp.toPx().toInt() }
    val alto = with(LocalDensity.current) { heightDp.toPx().toInt() }
    val path = remember { PathParser().parsePathString(
        "M367.51523-.27853597C244.01692.0896239 120.50872-.14339026-2.9934187 0v288.86506c427.0848087-.68895 854.0874287 1.82009 1281.1750187 1.05838V1.0583333c-130.6665.735932-261.4697-1.68497575-392.04975 1.0411367-59.84926 3.8798911-105.99386 45.409149-141.47617 90.001608-45.15692 48.446402-122.20171 48.976862-179.60946 24.953782-49.9117-26.866647-72.99577-87.827613-129.88951-104.744481C413.65029 3.9371635 390.56009-.07735769 367.51523-.27853597"
    ).toPath() }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val scaleX = size.width / ancho
        val scaleY = size.height / alto
        scale(scaleX, scaleY) {
            drawPath(path, color = fillColor)
        }
    }
}


@Preview(
    device = "id:pixel_9_pro", showSystemUi = false, showBackground = true,
    backgroundColor = 0xDFFFFFFF
)
@Composable
fun ClassDetailsPreview() {
    ClassDetails(
        onNavigateHome = {},
        showSnackbar = {},
    )
}
