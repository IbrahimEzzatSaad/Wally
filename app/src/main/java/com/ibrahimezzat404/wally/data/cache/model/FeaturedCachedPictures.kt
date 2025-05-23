package com.ibrahimezzat404.wally.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ibrahimezzat404.wally.data.api.model.PictureModel

@Entity(tableName = "cached_featured_pictures")
data class CachedFeaturedPicture (
    @PrimaryKey
    @ColumnInfo(name = "id") override var id: String = "",

    ): CachedPictureBase() {

    companion object {
        fun fromDomain(picture: PictureModel): CachedFeaturedPicture {
            return CachedFeaturedPicture().apply {
                id = picture.id
                description = picture.description
                altDescription = picture.altDescription
                color = picture.color
                likes = picture.likes
                links = picture.links
                artist = picture.user
                urls = picture.urls
                height = picture.height
                width = picture.width
            }
        }
    }
}