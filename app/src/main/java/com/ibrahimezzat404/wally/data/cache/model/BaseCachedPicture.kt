package com.ibrahimezzat404.wally.data.cache.model

import androidx.room.ColumnInfo
import com.ibrahimezzat404.wally.data.api.model.Artist
import com.ibrahimezzat404.wally.data.api.model.Links
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.data.api.model.Urls

open class CachedPictureBase (
    @ColumnInfo(name = "id") open var id: String = "",
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "alt_description") var altDescription: String? = "",
    @ColumnInfo(name = "color") var color: String = "",
    @ColumnInfo(name = "artist") var artist: Artist= Artist("",""),
    @ColumnInfo(name = "likes") var likes: Int = 0,
    @ColumnInfo(name = "urls") var urls: Urls = Urls("","","","","",""),
    @ColumnInfo(name = "links") var links: Links = Links(""),
    @ColumnInfo(name = "height") var height: Int = 0,
    @ColumnInfo(name = "width") var width: Int = 0,
) {
    fun toDomain(isFavorite: Boolean = false): PictureModel {
        return PictureModel(
            id = id,
            description = description,
            altDescription = altDescription,
            color = color,
            user = artist,
            likes = likes,
            links = links,
            urls = urls,
            height = height,
            width = width,
            favorite = isFavorite
        )
    }

}