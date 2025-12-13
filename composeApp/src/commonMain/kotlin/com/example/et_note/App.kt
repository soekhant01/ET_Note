package com.example.et_note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.et_note.model.Note
import com.example.et_note.notes.ListNotesScreen
import com.example.et_note.ui.theme.ETNoteAppTheme
import et_note.composeapp.generated.resources.Res
import et_note.composeapp.generated.resources.compose_multiplatform
import et_note.composeapp.generated.resources.rafiki
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() { //this function is starting point
    ETNoteAppTheme {
        val viewModel = viewModel { HomeViewModel() }
        val bottomSheet = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showBottomSheet = true
                    }, shape = CircleShape
                ) {
                    Text(text = "+", fontSize = 18.sp)
                }
            }) {
            val notes = viewModel.notes.collectAsStateWithLifecycle()
            Column(
                modifier = Modifier.padding(it)
            ) {
                Text(
                    text = "Notes",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    fontSize = 30.sp
                )
                if (viewModel.notes.value.isEmpty()) {
                    EmptyView()

                } else {
                    ListNotesScreen(viewModel.notes.value)
                }
            }
            if (showBottomSheet){
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = bottomSheet
                ){
                    AddItemDialog(
                        onCancel = {
                            coroutineScope.launch {
                                bottomSheet.hide()
                            }
                            showBottomSheet = false
                        }, onSave = {
                            viewModel.addNotes(it)
                            coroutineScope.launch {
                                bottomSheet.hide()
                            }
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddItemDialog(
    onSave: (Note) -> Unit,
    onCancel: () -> Unit
    ){
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val color = TextFieldDefaults.colors(
            focusedContainerColor = Transparent,
            unfocusedContainerColor = Transparent
        )
        TextField(
            value = title,
            onValueChange = {title = it},
            colors = color,
            placeholder = {
                Text("Title", fontSize = 22.sp)
            },
            textStyle = TextStyle(fontSize = 22.sp)
        )


        TextField(
            value = description,
            onValueChange = {description = it},
            colors = color,
            placeholder = {
                Text("Description")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "cancel",
                modifier = Modifier.padding(8.dp).clickable{
                    onCancel()
                }
            )

            Text(
                text = "save",
                modifier = Modifier.padding(8.dp).clickable{
                    onSave(Note(title,description))
                }
            )
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