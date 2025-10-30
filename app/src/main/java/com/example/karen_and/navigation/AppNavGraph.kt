package com.example.karen_and.navigation

import LoginScreen
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
import com.example.karen_and.screens.profile.ProfileScreen
import com.example.karen_and.screens.signup.SignUpScreen
import com.example.karen_and.screens.splash.SplashScreen
import com.example.karen_and.screens.teacher_screens.accept_student.AcceptStudentScreen
@Composable
fun AppNavGraph(
    navController: NavHostController,
    showSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.LOGIN) {
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
        composable(Routes.CLASSES) { ClassesScreen(modifier) }
        composable(Routes.ACCEPT_STUDENTS) { AcceptStudentScreen(modifier) }

    }
}