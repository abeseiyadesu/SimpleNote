package com.example.simplenoteapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simplenoteapp.data.freespace.FreeSpace
import com.example.simplenoteapp.data.freespace.FreeSpaceDao
import com.example.simplenoteapp.data.note.Note
import com.example.simplenoteapp.data.note.NoteDao

@Database(entities = [Note::class, FreeSpace::class], version = 2,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun freeSpaceDao(): FreeSpaceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDatabase"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance    // 戻り値
            }
        }
    }

}