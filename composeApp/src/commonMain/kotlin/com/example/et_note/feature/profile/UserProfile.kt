package com.example.et_note.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.et_note.data.cache.DataStoreManager

@Composable
fun UserProfile(dataStoreManager: DataStoreManager) {

    val email = remember { mutableStateOf("") }
    LaunchedEffect(true) {
        email.value = dataStoreManager.getEmail() ?: ""
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Text("user Profile", modifier = Modifier.padding(16.dp))
        Box(modifier = Modifier.padding(16.dp).weight(1f))
        Text("Email: ${email.value} ", modifier = Modifier.padding(16.dp))
        Box(modifier = Modifier.padding(16.dp).weight(1f))



    }
}