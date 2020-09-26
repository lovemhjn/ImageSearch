package com.app.imagesearch.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.imagesearch.data.local.db.dao.CommentsDao
import com.app.imagesearch.data.local.db.entity.CommentsEntity

@Database(entities = [CommentsEntity::class], version = 1, exportSchema = false)
public abstract class RoomDb : RoomDatabase() {

    abstract fun commentsDao(): CommentsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getDatabase(context: Context): RoomDb {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    "database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}