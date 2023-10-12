package com.example.wally.data.cache.model

import androidx.room.ColumnInfo
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.data.api.model.Urls
open class CachedPictureBase (
    @ColumnInfo(name = "id") open var id: String = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "alt_description") var altDescription: String? = "",
    @ColumnInfo(name = "color") var color: String = "",
    @ColumnInfo(name = "likes") var likes: Int = 0,
    @ColumnInfo(name = "links") var links: Urls = Urls("","","","","",""),
    @ColumnInfo(name = "height") var height: Int = 0,
    @ColumnInfo(name = "width") var width: Int = 0,
) {
    fun toDomain(isFavorite: Boolean = false): PicturesItem {
        return PicturesItem(
            id,
            description,
            altDescription,
            color,
            likes,
            links,
            height,
            width,
            isFavorite
        )
    }

}