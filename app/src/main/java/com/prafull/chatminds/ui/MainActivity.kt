package com.prafull.chatminds.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.prafull.chatminds.ui.theme.ChatMindsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatMindsTheme(darkTheme = true) {
                val majorNavController = rememberNavController()
                NavHost(navController = majorNavController, startDestination = "AUTH") {
                    authScreen(majorNavController)
                    composable("MAIN") {
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
    navigation(route = "AUTH", startDestination = "MAIN_AUTH") {
        composable("MAIN_AUTH") {
            Column {
                Text(text = "Auth Screen")
                Button(onClick = {
                    navController.navigate("MAIN")
                }) {
                    Text(text = "Login")
                }
            }
        }
    }
}
@Composable
fun MainScreen(finish: () -> Unit){
    val navController = rememberNavController()
    var exit by remember {
        mutableStateOf(false)
    }
    var currentScreen by rememberSaveable { mutableStateOf(Screens.NewChat.name) }
    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collectLatest {
            currentScreen = navController.currentDestination?.route ?: Screens.NewChat.name
        }
    }
    BackHandler {
        exit = true
    }
    if (exit) {
        ExitDialog {
            if (it) {
                finish()
            } else {
                exit = false
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (currentScreen != Screens.Chat.name) {
                BottomNavigationBar(navController = navController) {
                    currentScreen = it
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Main(navController = navController) {
                currentScreen = it
            }
        }
    }
}
@Composable
fun ExitDialog(exit: (Boolean) -> Unit){
    var openDialog by remember { mutableStateOf(true) }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
            text = {
                Text(
                    "Do you Want to Exit"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        exit(true)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        exit(false)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}
@Composable
fun BottomNavigationBar(navController: NavController, changeScreen: (String) -> Unit = {}) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        Screens.NewChat.name to Pair(Icons.Outlined.Home, Icons.Filled.Home),
        Screens.History.name to Pair(Icons.Outlined.Refresh, Icons.Filled.Refresh),
        Screens.Profile.name to Pair(Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle),
        Screens.Models.name to Pair(Icons.Outlined.Build, Icons.Filled.Build),
        Screens.Settings.name to Pair(Icons.Outlined.Settings, Icons.Filled.Settings),
    )
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 50f, topEnd = 50f))
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedItem == index) item.second.second else item.second.first,
                        contentDescription = null
                    )
                },
                label = { Text(item.first) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.popBackStack()
                    navController.navigate(item.first)
                    changeScreen(item.first)
                }
            )
        }
    }
}
enum class Screens {
    NewChat,
    History,
    Profile,
    Models,
    Settings,
    Chat
}
fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}