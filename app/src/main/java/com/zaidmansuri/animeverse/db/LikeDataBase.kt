package com.zaidmansuri.animeverse.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zaidmansuri.animeverse.model.WallpaperModel


@Database(entities = [WallpaperModel::class], version = 1, exportSchema = false)
abstract class LikeDataBase : RoomDatabase() {
    abstract fun likeDao(): LikeDao

    companion object {
        private var INSTANCE: LikeDataBase? = null
        fun getDatabase(context: Context): LikeDataBase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,LikeDataBase::class.java, "like_db")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}