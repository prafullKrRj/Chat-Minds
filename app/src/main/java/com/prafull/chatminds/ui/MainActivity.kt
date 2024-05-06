package com.prafull.chatminds.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.prafull.chatminds.features.onBoardAuth.ui.AuthScreen
import com.prafull.chatminds.features.subscriptions.ui.SubscriptionScreen
import com.prafull.chatminds.ui.theme.ChatMindsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContent {
            ChatMindsTheme(darkTheme = true) {
                val majorNavController = rememberNavController()
                var startDestination by rememberSaveable {
                    mutableStateOf(MajorScreens.Auth.name)
                }
                if (mAuth.currentUser != null) {
                    startDestination = MajorScreens.Main.name
                }
                NavHost(navController = majorNavController, startDestination = MajorScreens.Auth.name) {
                    authScreen(majorNavController, mAuth)
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

fun NavGraphBuilder.authScreen(
    navController: NavController,
    mAuth: FirebaseAuth
) {
    navigation(route = MajorScreens.Auth.name, startDestination = Auth.Login.name) {
        composable(Auth.Login.name) {
            AuthScreen(navController = navController, mAuth)
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