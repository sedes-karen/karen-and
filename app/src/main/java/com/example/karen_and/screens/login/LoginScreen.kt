package com.example.karen_and.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.karen_and.R
import com.example.karen_and.models.UserRole
import com.example.karen_and.ui.components.AppButton
import com.example.karen_and.ui.components.AppInput
import com.example.karen_and.ui.theme.AppTypography
import com.example.karen_and.ui.ui_events.UIEvents

@Composable
fun LoginScreen(
    onNavigateHome: () -> Unit,
    onNavigateSignUp: () -> Unit,
    showSnackbar: (String) -> Unit,
) {
    val context = LocalContext.current

    val tokenStore = remember {
        val appCtx = context.applicationContext
        val prefs = appCtx.getSharedPreferences("karen_prefs", android.content.Context.MODE_PRIVATE)
        com.example.karen_and.data.TokenStore(prefs)
    }
    // ViewModel con factory
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(tokenStore)
    )


    val state = viewModel.state.collectAsState().value
    val image = painterResource(R.drawable.logo_karen)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UIEvents.ShowSnackbar -> showSnackbar(event.message)
                else -> {}
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp)
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
        )

        Text(
            text = stringResource(R.string.login_title),
            style = AppTypography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(R.string.email_label),
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .padding(horizontal = 16.dp)
        )

        AppInput(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            placeholder = stringResource(R.string.email_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            hasBorder = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.password_label),
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .padding(horizontal = 16.dp)
        )

        AppInput(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            hasBorder = false
        )

        Spacer(modifier = Modifier.height(32.dp))

        AppButton(
            text = stringResource(R.string.login_button_text),
            onClick = { viewModel.submit(navigateToHome = onNavigateHome) },
            enabled = state.isFormValid,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.login_text),
                style = AppTypography.titleMedium,
                color = Color.Gray,
                modifier = Modifier
                    .padding(end = 10.dp)
            )

            Image(
                painter = painterResource(R.drawable.google_logo),
                contentDescription = "Login con Google",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(R.drawable.facebook_logo),
                contentDescription = "Login con Facebook",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(R.drawable.github_logo),
                contentDescription = "Login con GitHub",
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.register_link_title),
                style = AppTypography.bodyMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.register_link),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateSignUp() }
            )
        }
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