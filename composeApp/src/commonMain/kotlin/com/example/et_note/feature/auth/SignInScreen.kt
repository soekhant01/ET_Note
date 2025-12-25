package com.example.et_note.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SignInScreen(navController: NavController) {
    val viewModel = viewModel { SignUpViewModel() }

    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign Up", fontSize = 22.sp)
        Spacer(Modifier.size(16.dp))

//        email text field
        OutlinedTextField(
            email.value,
            onValueChange = {
                viewModel.onEmailUpdate(it)
            },
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            placeholder = {
                Text("Email")
            },
            label = {
                Text("Email")
            }

        )
        Spacer(Modifier.size(16.dp))

//        password text field
        OutlinedTextField(
            password.value,
            onValueChange = {
                viewModel.onPasswordUpdate(it)
            },
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
            placeholder = {
                Text("Password")
            },
            label = {
                Text("Password")
            }

        )
        Spacer(Modifier.size(16.dp))


        Spacer(Modifier.size(16.dp))

        TextButton(
            {
                navController.popBackStack()
            },
        ){
            Text("Don't have an account? Login")
        }
        Spacer(Modifier.size(16.dp))

        Button(onClick = {
            viewModel.signUp()
        }, modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)){
            Text("Submit")
        }


    }
}