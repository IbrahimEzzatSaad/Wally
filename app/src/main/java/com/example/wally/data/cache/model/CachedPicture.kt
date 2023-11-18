package com.example.wally.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wally.data.api.model.PictureModel

@Entity(tableName = "cached_pictures")
data class CachedPicture (

    @PrimaryKey
    @ColumnInfo(name = "id") override var id: String = "",
    @ColumnInfo(name = "favorite") var favorite: Boolean = false,


    ) : CachedPictureBase(){
    companion object {
        fun fromDomain(picture: PictureModel, isFavorite : Boolean): CachedPicture {
            return CachedPicture().apply {
                id = picture.id
                description = picture.description
                altDescription = picture.altDescription
                color = picture.color
                likes = picture.likes
                links = picture.urls
                height = picture.height
                width = picture.width
                favorite = isFavorite
            }
        }
    }
}

