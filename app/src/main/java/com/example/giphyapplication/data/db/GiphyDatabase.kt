package com.example.giphyapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.giphyapplication.data.db.dao.FavoriteDao
import com.example.giphyapplication.data.db.entity.FavoriteItem
import com.example.giphyapplication.utils.Constant.DATABASE_NAME

@Database(entities = [FavoriteItem::class], version = 5, exportSchema = false)

abstract class GiphyDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: GiphyDatabase? = null

        fun getDatabase(context: Context): GiphyDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, GiphyDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}