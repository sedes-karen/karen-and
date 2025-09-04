package com.example.karen_and.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.karen_and.screens.login.LoginScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    showSnackbar: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateHome = {
                    navController.navigate("home") {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                },
                onNavigateSignUp = {
                    navController.navigate("home") {
                        popUpTo(Routes.SIGN_UP) {
                            inclusive = true
                        }
                    }
                },
              showSnackbar = showSnackbar,
            )
        }
    }

}