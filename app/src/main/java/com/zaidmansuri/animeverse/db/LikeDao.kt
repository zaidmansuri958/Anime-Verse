package com.zaidmansuri.animeverse.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaidmansuri.animeverse.model.WallpaperModel
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {
    @Query("SELECT * FROM likesTable")
    fun getLikes(): LiveData<List<WallpaperModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLike(likeRequest: WallpaperModel)

    @Query("SELECT EXISTS(SELECT * FROM likesTable Where image=:img)")
    suspend fun likedOrNot(img:String):Boolean

    @Query("DELETE FROM likesTable Where image=:img")
    suspend fun removeLike(img: String)

}