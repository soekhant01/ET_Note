package com.example.et_note

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.et_note.data.cache.DataStoreManager
import com.example.et_note.data.cache.createDataStore
import com.example.et_note.data.db.getNoteDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        setContent {
            App(
                database = getNoteDatabase(getDatabaseBuilder(this@MainActivity)),
                dataStoreManager = DataStoreManager(createDataStore(this@MainActivity))
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val database = getNoteDatabase(getDatabaseBuilder(LocalContext.current))
    val dataStoreManager = DataStoreManager(com.example.et_note.createDataStore(LocalContext.current))

    App(database, dataStoreManager )
}


private fun displayToast(context: Context) {
    Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
}