package com.example.karen_and.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.karen_and.R
import com.example.karen_and.navigation.Routes
import com.example.karen_and.ui.components.AppButton
import com.example.karen_and.ui.components.AppInput
import com.example.karen_and.ui.theme.AppTypography
import com.example.karen_and.ui.ui_events.UIEvents
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import java.util.*
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignUpScreen(
    onNavigateLogin: () -> Unit,
    onNavigateHome: () -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState().value
    val image = painterResource(R.drawable.logo_karen)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UIEvents.ShowSnackbar -> showSnackbar(event.message)
                is UIEvents.Navigate -> {
                    when (event.route) {
                        Routes.HOME -> onNavigateHome()
                        Routes.LOGIN -> onNavigateLogin()
                        else -> {}
                    }
                }
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
                painter = painterResource(R.drawable.register),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 40.dp)
                    .size(300.dp)
            )

            Text(
                text = stringResource(R.string.register_title),
                style = AppTypography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            StepIndicator(currentStep = state.currentStep)

            AnimatedContent(
                targetState = state.currentStep,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "stepTransition"
            ) { step ->
                when (step) {
                    1 -> StepOne(state, viewModel)
                    2 -> StepTwo(state, viewModel)
                    3 -> StepThree(state, viewModel)
                    4 -> StepFour(state, viewModel, onNavigateHome, showSnackbar)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                AppButton(
                    text = if (state.currentStep == 4) {
                        stringResource(R.string.finish_button_text)
                    } else {
                        stringResource(R.string.continue_button_text)
                    },
                    onClick = {
                        if (state.currentStep == 4) {
                            viewModel.submit()
                        } else {
                            viewModel.nextStep()
                        }
                    },
                    enabled = state.isFormValid && !state.isLoading ) }

            Spacer(modifier = Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    contentDescription = "Registrarse con Google",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(R.drawable.facebook_logo),
                    contentDescription = "Registrarse con Facebook",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(R.drawable.github_logo),
                    contentDescription = "Registrarse con GitHub",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
            Spacer(modifier = Modifier.height(15.dp))

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
                    text = stringResource(R.string.login_title),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateLogin() }
                )
            }
        }}
    }

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onNavigateHome = {},
        onNavigateLogin = {},
        showSnackbar = {},
        )
}


@Composable
fun StepIndicator(
    currentStep: Int,
    totalSteps: Int = 4
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..totalSteps) {
            val isActive = i < currentStep

            val circleColor by animateColorAsState(
                targetValue = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                label = "circleColor"
            )

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(color = circleColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isActive) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            if (i < totalSteps) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(40.dp)
                        .background(
                            if (i < currentStep) MaterialTheme.colorScheme.surface else Color.LightGray
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun StepOne(state: SignUpState, viewModel: SignUpViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.name_label),
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .padding(horizontal = 16.dp)
        )

        AppInput(
            value = state.name,
            onValueChange = {
                viewModel.onNameChange(it) },
            hasBorder = false
        )

        if (state.nameError != null) {
            Text(
                text = state.nameError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}

@Composable
fun StepTwo(state: SignUpState, viewModel: SignUpViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.lastname_label),
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .padding(horizontal = 16.dp)
        )

        AppInput(
            value = state.lastname,
            onValueChange = {
                viewModel.onLastnameChange(it) },
            hasBorder = false
        )

        if (state.lastnameError != null) {
            Text(
                text = state.lastnameError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}

@Composable
fun StepThree(state: SignUpState, viewModel: SignUpViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            hasBorder = false
        )

        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}

@Composable
fun StepFour(state: SignUpState, viewModel: SignUpViewModel, onNavigateHome: () -> Unit, showSnackbar: (String) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UIEvents.ShowSnackbar -> showSnackbar(event.message)
                is UIEvents.Navigate -> {
                    if (event.route == Routes.HOME) onNavigateHome()
                }
                else -> {}
            }
        }
    }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            hasBorder = false,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.password_confirm),
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .padding(horizontal = 16.dp)
        )

        AppInput(
            value = state.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            hasBorder = false,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (state.confirmPasswordError != null) {
            Text(
                text = state.confirmPasswordError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}

// Fecha de cumpleaños
//fun StepTwo(state: SignUpState, viewModel: SignUpViewModel) {
//    val context = LocalContext.current
//    val calendar = Calendar.getInstance()
//
//    var selectedDate by remember { mutableStateOf("") }
//
//    val datePickerDialog = DatePickerDialog(
//        context,
//        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
//            selectedDate = "$dayOfMonth/${month + 1}/$year"
//            viewModel.onBirthdayChange(selectedDate)
//        },
//        calendar.get(Calendar.YEAR),
//        calendar.get(Calendar.MONTH),
//        calendar.get(Calendar.DAY_OF_MONTH)
//    )
//
//    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
//
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//
//        Text(text = "Fecha seleccionada: $selectedDate")
//        AppButton(
//            text = stringResource(R.string.date_label),
//            onClick = { datePickerDialog.show() },
//            enabled = state.isFormValid && !state.isLoading
//        )
//
//        if (state.birthdayError != null) {
//            Text(
//                text = state.birthdayError,
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
//            )
//        }
//
//        Spacer(Modifier.height(24.dp))
//
//        Row(horizontalArrangement = Arrangement.Center) {
//            AppButton(
//                text = stringResource(R.string.continue_button_text),
//                onClick = { viewModel.nextStep() },
//                enabled = state.isFormValid && !state.isLoading
//            )
//        }
//    }
//}