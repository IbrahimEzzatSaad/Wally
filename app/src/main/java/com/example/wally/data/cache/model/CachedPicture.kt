package com.example.wally.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.data.api.model.Urls

@Entity(tableName = "pictures")
data class CachedPicture (
    @PrimaryKey(autoGenerate = true)
    val pictureId: Int = 0,
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "alt_description") var altDescription: String? = "",
    @ColumnInfo(name = "color") var color: String = "",
    @ColumnInfo(name = "likes") var likes: Int = 0,
    @ColumnInfo(name = "links") var links: Urls = Urls("","","","","",""),
    @ColumnInfo(name = "height") var height: Int = 0,
    @ColumnInfo(name = "width") var width: Int = 0,
    @ColumnInfo(name = "featured") var featured: Boolean = false

){
    companion object {
        fun fromDomain(picture: PicturesItem): CachedPicture {
            return CachedPicture(
                id = picture.id,
                description = picture.description ,
                altDescription = picture.altDescription,
                color = picture.color,
                likes = picture.likes,
                links = picture.urls,
                height = picture.height,
                width = picture.width,
                featured = picture.featured
            )
        }

    }
}

fun CachedPicture.toDomain(): PicturesItem {

    return PicturesItem(
        id,
        description,
        altDescription,
        color,
        likes,
        links,
        height,
        width,
        featured
    )
}