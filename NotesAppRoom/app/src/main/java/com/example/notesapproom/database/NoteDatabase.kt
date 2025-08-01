package com.example.notesapproom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapproom.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao() : NoteDao

    companion object{
        @Volatile
        private var instance: NoteDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance?:
        synchronized(LOCK){
            instance?:
            createDatabase(context).also{
                instance=it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes_db"
            ).build()
    }
}