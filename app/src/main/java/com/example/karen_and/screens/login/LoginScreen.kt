package com.example.karen_and.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karen_and.R
import com.example.karen_and.ui.components.AppButton
import com.example.karen_and.ui.theme.AppTypography
import com.example.karen_and.ui.ui_events.UIEvents

@Composable
fun LoginScreen(
    onNavigateHome: () -> Unit,
    onNavigateSignUp: () -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UIEvents.ShowSnackbar -> showSnackbar(event.message)
                else -> {}
            }
        }
    }


    Column {
        //TODO: añadir la imagen
        Text(stringResource(R.string.login_title), style = AppTypography.titleSmall)

        //TODO: los textfields tienen un label arriba y el mail un placeholder
        TextField(
            value = state.email,
            onValueChange = {
                viewModel.onEmailChange(it)
            },
        )

        AppButton(stringResource(R.string.login_button_text)) {
            viewModel.submit()
        }

        //TODO: añadir los iconitos/botoncitos de login con google, facebook y github, pero NO implementarlos por ahora
        Row {
            //TODO: texto de "iniciar con" (no lo hardcodeen, añadanlo en los strings y usen ese stringResource(R.strings.string_name))

            // TODO: iconitos
        }

        //TODO: parte de redirect a sign up y el link

    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onNavigateHome = {},
        onNavigateSignUp = {},
        showSnackbar = {},
    )
}