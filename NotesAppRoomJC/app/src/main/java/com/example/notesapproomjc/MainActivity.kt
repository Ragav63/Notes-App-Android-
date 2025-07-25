package com.example.notesapproomjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapproomjc.data.NoteDatabase
import com.example.notesapproomjc.repository.NoteRepository
import com.example.notesapproomjc.ui.theme.NotesAppRoomJCTheme
import com.example.notesapproomjc.view.AddNoteScreen
import com.example.notesapproomjc.view.EditNoteScreen
import com.example.notesapproomjc.view.HomeScreen
import com.example.notesapproomjc.viewmodel.NoteViewModel
import com.example.notesapproomjc.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NotesAppRoomJCTheme {
                val context = applicationContext

                // Create your repository and factory
                val noteRepository = remember { NoteRepository(NoteDatabase(context)) }
                val factory = remember { NoteViewModelFactory(application, noteRepository) }

                // Get the ViewModel
                val noteViewModel: NoteViewModel = viewModel(factory = factory)

                // Navigation controller
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",  // Set AddNoteScreen as start destination
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(navController = navController, noteViewModel = noteViewModel)
                        }

                        composable("addNote") {
                            AddNoteScreen(navController = navController, noteViewModel = noteViewModel)
                        }

                        composable("editNote/{noteId}") { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()

                            // Get note from the ViewModel (you must expose notes as StateFlow/List)
                            val notes = noteViewModel.notes.collectAsState(initial = emptyList()).value
                            val note = notes.find { it.id == noteId }

                            note?.let {
                                EditNoteScreen(
                                    navController = navController,
                                    noteViewModel = noteViewModel,
                                    note = it
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
