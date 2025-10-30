package com.example.karen_and.screens.teacher_screens.accept_student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karen_and.ui.components.AppButton
import com.example.karen_and.ui.theme.AppTypography
import com.example.karen_and.ui.theme.KarenandTheme

@Composable
fun AcceptStudentScreen(
    modifier: Modifier = Modifier,
    viewModel: AcceptStudentViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }


    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Text("Acceptar alumnos", style = AppTypography.titleMedium, textAlign = TextAlign.Center)
        Row {

            AppButton(
                text = "Aceptar",
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)
            ) { }

            AppButton(
                text = "Eliminar",
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)
            ) { }
        }
        if (state.isLoadingGetUsers) {
            CircularProgressIndicator()
        } else if (state.errorMessage != null) {
            Text("Error: ${state.errorMessage}", color = Color.Red)
        } else {
            LazyColumn {
                items(state.students.size) { index ->
                    Text("${state.students.get(index).name} ${state.students.get(index).lastname}")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AcceptStudentScreenPreview() {
    KarenandTheme { // o el nombre de tu theme
        AcceptStudentScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        )
    }
}