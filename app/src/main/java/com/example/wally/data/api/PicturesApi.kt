package com.example.wally.data.api

import com.example.wally.data.api.model.ApiPictures
import retrofit2.http.GET
import retrofit2.http.Query

interface PicturesApi {

    @GET(ApiConstants.BASE_ENDPOINT )
    suspend fun getPictures(
        @Query("page") page: String,
        @Query("client_id") apiKey: String =  "2HpqABX4l5_2gjoWXk00S8WA52Ib184gYQbAth9sjv0"

    ): ApiPictures

}