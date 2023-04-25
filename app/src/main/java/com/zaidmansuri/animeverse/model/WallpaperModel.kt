package com.zaidmansuri.animeverse.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likesTable")
data class WallpaperModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    var image: String?=null,
    val name: String?=null
)
