package com.example.notesapproomjc.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notesapproom.model.Note
import com.example.notesapproomjc.R
import com.example.notesapproomjc.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    noteViewModel: NoteViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val notes by noteViewModel.notes.collectAsState()

    // Initial load of all notes
    LaunchedEffect(Unit) {
        noteViewModel.getAllNotes()
    }

    // Search effect
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            noteViewModel.searchNote("%$searchQuery") // Search based on query

        } else {
            // Reset to full list
            noteViewModel.getAllNotes()
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Notes") }
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search notes...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.pink),
                        unfocusedBorderColor = colorResource(id = R.color.pink),
                        cursorColor = colorResource(id = R.color.pink)
                    )
                )

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addNote") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (notes.isEmpty()) {
                EmptyNotesView()
            } else {
                NotesGrid(
                    notes = notes,
                    modifier = Modifier.fillMaxSize(),
                    onNoteClick = { selectedNote ->
                        navController.navigate("editNote/${selectedNote.id}")
                    }
                )
            }
        }
    }
}


@Composable
fun EmptyNotesView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ntes),
            contentDescription = "No Notes",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("No Notes Found", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun NotesGrid(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    onNoteClick: (Note) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) { note ->
            NoteItem(note = note, onClick = onNoteClick)
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClick: (Note) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick(note) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp,  colorResource(id = R.color.pink)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.noteTitle,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.noteDesc,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

