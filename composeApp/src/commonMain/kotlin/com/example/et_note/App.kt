package com.example.et_note

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.et_note.data.cache.DataStoreManager
import com.example.et_note.data.db.NoteDatabase
import com.example.et_note.feature.auth.SignInScreen
import com.example.et_note.feature.auth.SignUpScreen
import com.example.et_note.feature.home.HomeScreen
import com.example.et_note.feature.profile.UserProfile
import com.example.et_note.ui.theme.ETNoteAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(database: NoteDatabase, dataStoreManager: DataStoreManager) { //this function is starting point

    ETNoteAppTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "home"){
            composable(route = "home") {
                HomeScreen(database, dataStoreManager,navController )
            }

            composable(route = "signup") {
                SignUpScreen(navController,dataStoreManager)
            }

            composable(route = "signIn") {
                SignInScreen(navController, dataStoreManager)
            }

            composable(route = "profile") {
                UserProfile(dataStoreManager)
            }
        }
    }
}

