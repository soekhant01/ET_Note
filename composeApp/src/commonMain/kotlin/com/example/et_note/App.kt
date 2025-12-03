package com.example.et_note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.et_note.notes.ListNotesScreen
import com.example.et_note.ui.theme.ETNoteAppTheme
import et_note.composeapp.generated.resources.Res
import et_note.composeapp.generated.resources.compose_multiplatform
import et_note.composeapp.generated.resources.rafiki
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() { //this function is starting point
    ETNoteAppTheme {
        val viewModel = viewModel { HomeViewModel() }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {}, shape = CircleShape
                ) {
                    Text(text = "+", fontSize = 18.sp)
                }
            }) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Text(
                    text = "Notes",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    fontSize = 30.sp
                )
                if (viewModel.notes.value.isEmpty()) {
                    ListNotesScreen(viewModel.notes.value)
                } else {
                    EmptyView()
                }
            }
        }
    }
}

@Composable
fun EmptyView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painterResource(Res.drawable.rafiki),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Create Your First Note",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp,
            )
        }
    }
}