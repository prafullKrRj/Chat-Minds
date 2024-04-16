package com.prafull.chatminds.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.prafull.chatminds.features.onBoardAuth.ui.AuthScreen
import com.prafull.chatminds.features.subscriptions.ui.SubscriptionScreen
import com.prafull.chatminds.ui.theme.ChatMindsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatMindsTheme(darkTheme = true) {
                val majorNavController = rememberNavController()
                NavHost(navController = majorNavController, startDestination = MajorScreens.Auth.name) {
                    authScreen(majorNavController)
                    composable(MajorScreens.Main.name) {
                        MainScreen {
                            finish()
                        }
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.authScreen(navController: NavController) {
    navigation(route = MajorScreens.Auth.name, startDestination = Auth.Login.name) {
        composable(Auth.Login.name) {
            AuthScreen(navController)
        }
        composable(Auth.Subscriptions.name) {
            SubscriptionScreen(navController)
        }
    }
}
enum class MajorScreens {
    Auth,
    Main
}
enum class Auth {
    Login,
    Subscriptions,
}
fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}