package com.example.wally.data.api

import com.example.wally.data.api.ApiParameters.API_KEY
import com.example.wally.data.api.ApiParameters.PAGE_SIZE
import com.example.wally.data.api.model.ApiPictures
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PicturesApi {

    @GET(ApiConstants.BASE_ENDPOINT )
    suspend fun getPictures(
        @Query("page") page: Long,
        @Query("per_page") numberItems : Int = PAGE_SIZE,
        @Query("client_id") apiKey: String =  API_KEY

    ): ApiPictures

    @GET(ApiConstants.BASE_ENDPOINT )
    suspend fun getFeatured(
        @Query("order_by") sort : String = "featured",
        @Query("per_page") numberItems : Int = 10,
        @Query("client_id") apiKey: String =  API_KEY
    ): ApiPictures


    @GET(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun getCategories(
        @Path("id") id: String, // Dynamic value to be included in the URL
        @Query("page") page: Long,
        @Query("per_page") numberItems: Int = PAGE_SIZE,
        @Query("client_id") apiKey: String = API_KEY
    ): ApiPictures
}