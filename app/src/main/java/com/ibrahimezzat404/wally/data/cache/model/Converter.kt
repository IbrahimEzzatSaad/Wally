package com.ibrahimezzat404.wally.data.cache.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ibrahimezzat404.wally.data.api.model.Artist
import com.ibrahimezzat404.wally.data.api.model.Links
import com.ibrahimezzat404.wally.data.api.model.Urls
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromUrls(url: Urls) : String? {
        return gson.toJson(url)
    }

    @TypeConverter
    fun toUrls(urls: String) : Urls{
        val objectType = object : TypeToken<Urls>() {}.type
        return gson.fromJson(urls, objectType)
    }

    @TypeConverter
    fun fromLinks(links: Links) : String? {
        return gson.toJson(links)
    }

    @TypeConverter
    fun toLinks(links: String) : Links{
        val objectType = object : TypeToken<Links>() {}.type
        return gson.fromJson(links, objectType)
    }

    @TypeConverter
    fun fromUser(user: Artist) : String? {
        return gson.toJson(user)
    }

    @TypeConverter
    fun toUser(user: String) : Artist{
        val objectType = object : TypeToken<Artist>() {}.type
        return gson.fromJson(user, objectType)
    }
}