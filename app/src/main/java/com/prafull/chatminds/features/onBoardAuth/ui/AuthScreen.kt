package com.prafull.chatminds.features.onBoardAuth.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.prafull.chatminds.R
import com.prafull.chatminds.ui.MajorScreens

@Composable
fun AuthScreen(
    navController: NavController,
    mAuth: FirebaseAuth,
) {

    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(R.string.web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val signInLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        val exception = task.exception
        val account = task.getResult(ApiException::class.java)!!
        if (task.isSuccessful) {
            try {
                val account = task.getResult(ApiException::class.java)!!
                mAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate(MajorScreens.Main.name)
                        } else {
                            Toast.makeText(context, "Google SignIn Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                Toast.makeText(context, "Google SignIn Failed task", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Google SignIn Failed $exception", Toast.LENGTH_SHORT).show()
        }
    }
    Button(onClick = {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }) {
        Text(text = "Auth Screen")
    }
}