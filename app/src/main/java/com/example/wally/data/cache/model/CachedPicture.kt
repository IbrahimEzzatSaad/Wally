package com.example.wally.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.data.api.model.Urls

@Entity(tableName = "pictures")
data class CachedPicture (
    @PrimaryKey(autoGenerate = true)
    var pictureId: Int = 0,
    @ColumnInfo(name = "code") var code: String = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "alt_description") var altDescription: String? = "",
    @ColumnInfo(name = "color") var color: String = "",
    @ColumnInfo(name = "likes") var likes: Int = 0,
    @ColumnInfo(name = "links") var links: Urls = Urls("","","","","",""),
    @ColumnInfo(name = "height") var height: Int = 0,
    @ColumnInfo(name = "width") var width: Int = 0
){
    companion object {
        fun fromDomain(picture: PicturesItem): CachedPicture {
            return CachedPicture(
                picture.idd,
                picture.code,
                picture.description ,
                picture.altDescription,
                picture.color,
                picture.likes,
                picture.urls,
                picture.height,
                picture.width
            )
        }

    }
}

fun CachedPicture.toDomain(): PicturesItem {

    return PicturesItem(
        pictureId,
        code,
        description,
        altDescription,
        color,
        likes,
        links,
        height,
        width
    )
}