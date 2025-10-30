package com.example.karen_and.screens.teacher_screens.accept_student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karen_and.models.UserStatusEnum
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
            ) {
                viewModel.acceptSelectedSequential(UserStatusEnum.ENABLED)
            }

            AppButton(
                text = "Eliminar",
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)
            ) {
                viewModel.acceptSelectedSequential(UserStatusEnum.DISABLED) }
        }
        if (state.isLoadingGetUsers) {
            CircularProgressIndicator()
        } else if (state.errorMessage != null) {
            Text("Error: ${state.errorMessage}", color = Color.Red)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp),


            ) {
                items(state.students.size) { i ->
                    val u = state.students[i]
                    val fullName = listOfNotNull(u.name, u.lastname.takeIf { it.isNotBlank() })
                        .joinToString(" ")
                    val selected = u.id in state.studentsSelected
                    val dateRight: String? = null

                    AcceptStudentItem(
                        name = fullName,
                        email = u.email,
                        dateRight = dateRight,
                        selected = selected,
                        onToggleSelect = { viewModel.toggleSelection(u.id) },
                        modifier = Modifier
                    )
                }

                item { Spacer(Modifier.height(16.dp)) }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AcceptStudentScreenPreview() {
    KarenandTheme {
        AcceptStudentScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        )
    }
}



@Composable
fun AcceptStudentItem(
    name: String,
    email: String,
    dateRight: String?,
    selected: Boolean,
    onToggleSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerShape = RoundedCornerShape(20.dp)
    val chipShape = RoundedCornerShape(10.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(containerShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable() { onToggleSelect() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(chipShape)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)),
            contentAlignment = Alignment.Center
        ) {
            val innerAlpha = if (selected) 1f else 0f
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = innerAlpha))
            )
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (!dateRight.isNullOrBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = dateRight,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}