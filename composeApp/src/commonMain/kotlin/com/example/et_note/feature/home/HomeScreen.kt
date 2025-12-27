package com.example.et_note.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.et_note.HomeViewModel
import com.example.et_note.data.cache.DataStoreManager
import com.example.et_note.data.db.NoteDatabase
import com.example.et_note.model.Note
import com.example.et_note.notes.ListNotesScreen
import et_note.composeapp.generated.resources.Res
import et_note.composeapp.generated.resources.rafiki
import et_note.composeapp.generated.resources.user
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(database: NoteDatabase,dataStoreManager: DataStoreManager, navController: NavController) {
    val viewModel = viewModel { HomeViewModel(database, dataStoreManager = dataStoreManager) }
    val bottomSheet = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val email = remember { mutableStateOf("") }
    LaunchedEffect(true){
        email.value = dataStoreManager.getEmail() ?: ""
    }
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
        val notes = viewModel.notes.collectAsStateWithLifecycle(emptyList())
        Column(
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Notes",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    fontSize = 30.sp
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    email?.value?.let {
                        Text(it)
                    }
                    Image(
                        painterResource(Res.drawable.user),
                        null,
                        modifier = Modifier.padding(end = 16.dp).size(48.dp).padding(4.dp)
                            .clickable {
                                coroutineScope.launch {
                                    if (dataStoreManager.getToken() != null) navController.navigate("profile")
                                    else navController.navigate("signup")
                                }
                            }
                    )
                }
            }
            if (notes.value.isEmpty()) {
                EmptyView()

            } else {
                ListNotesScreen(notes.value)
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = bottomSheet
            ) {
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

@Composable
fun AddItemDialog(
    onSave: (Note) -> Unit,
    onCancel: () -> Unit
) {
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
            onValueChange = { title = it },
            colors = color,
            placeholder = {
                Text("Title", fontSize = 22.sp)
            },
            textStyle = TextStyle(fontSize = 22.sp),
            modifier = Modifier.fillMaxWidth()
        )


        TextField(
            value = description,
            onValueChange = { description = it },
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
                modifier = Modifier.padding(8.dp).clickable {
                    onCancel()
                }
            )

            Text(
                text = "save",
                modifier = Modifier.padding(8.dp).clickable {
                    onSave(Note(title, description))
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
