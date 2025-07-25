package com.example.notesapproomjc.view

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesapproom.model.Note
import com.example.notesapproomjc.R
import com.example.notesapproomjc.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    note: Note
) {
    var noteTitle by remember { mutableStateOf(note.noteTitle) }
    var noteDesc by remember { mutableStateOf(note.noteDesc) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        AlertDialog.Builder(context).apply {
                            setTitle("Delete note")
                            setMessage("Do you want to delete this note?")
                            setPositiveButton("Delete") { _, _ ->
                                noteViewModel.deleteNote(note)
                                navController.popBackStack()
                            }
                            setNegativeButton("Cancel", null)
                        }.create().show()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (noteTitle.isNotBlank()) {
                        val updatedNote = Note(note.id, noteTitle.trim(), noteDesc.trim())
                        noteViewModel.updateNote(updatedNote)
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please enter note title", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Save")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = com.example.notesapproomjc.R.color.pink),
                    unfocusedBorderColor = colorResource(id = com.example.notesapproomjc.R.color.pink),
                    cursorColor = colorResource(id = R.color.pink)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = noteDesc,
                onValueChange = { noteDesc = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.pink),
                    unfocusedBorderColor = colorResource(id = R.color.pink),
                    cursorColor = colorResource(id = R.color.pink)
                )
            )
        }
    }
}