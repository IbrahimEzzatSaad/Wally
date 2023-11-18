package com.example.wally.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wally.data.api.model.PictureModel

@Entity(tableName = "cached_favorite_pictures")
data class CachedFavoritePicture (
    @PrimaryKey
    @ColumnInfo(name = "id") override var id: String = "",

    ): CachedPictureBase() {

    companion object {
        fun fromDomain(picture: PictureModel): CachedFavoritePicture {
            return CachedFavoritePicture().apply {
                id = picture.id
                description = picture.description
                altDescription = picture.altDescription
                color = picture.color
                likes = picture.likes
                links = picture.urls
                height = picture.height
                width = picture.width
            }
        }

    }
}