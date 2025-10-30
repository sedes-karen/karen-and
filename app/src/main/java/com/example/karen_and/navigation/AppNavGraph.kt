package com.example.karen_and.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.karen_and.screens.chat.ChatScreen
import com.example.karen_and.screens.classes.ClassesScreen
import com.example.karen_and.screens.home.HomeScreen
import com.example.karen_and.screens.login.LoginScreen
import com.example.karen_and.screens.login.LoginViewModel
import com.example.karen_and.screens.profile.ProfileScreen
import com.example.karen_and.screens.signup.SignUpScreen
import com.example.karen_and.ui.ui_events.UIEvents

@Composable
fun AppNavGraph(
    navController: NavHostController,
    showSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None }

    ) {
        composable(Routes.LOGIN) {
            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(
                onNavigateHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                },
                onNavigateSignUp = {
                    navController.navigate(Routes.SIGN_UP) {
                        popUpTo(Routes.SIGN_UP) {
                            inclusive = true
                        }
                    }
                },
                showSnackbar = showSnackbar,
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                modifier = modifier,
                onNavigateToClasses = { navController.navigate(Routes.CLASSES) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToChat = { navController.navigate(Routes.CHAT) },
            )
        }
        composable(Routes.CLASSES) {
            ClassesScreen(modifier = modifier)
        }
        composable(Routes.PROFILE) { ProfileScreen(modifier) }
        composable(Routes.CHAT) { ChatScreen(modifier) }
        composable(Routes.CLASSES) { ClassesScreen(modifier) }

        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onNavigateLogin = {
                    navController.navigateUp()
                },
                onNavigateHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                showSnackbar = showSnackbar
            )
        }
    }

}