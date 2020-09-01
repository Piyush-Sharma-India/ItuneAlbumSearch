package com.android.example.myapplication.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.myapplication.data.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
}