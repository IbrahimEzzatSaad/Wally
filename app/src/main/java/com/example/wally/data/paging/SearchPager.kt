package com.example.wally.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wally.data.api.PicturesApi
import com.example.wally.data.api.model.PictureModel

class SearchPagingSource(
    private val picturesApi: PicturesApi,
    private val query : String
) : PagingSource<Int, PictureModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureModel> {
        try {
            // Start by defining the page key and the page size
            val currentPage = params.key ?: 1

            // Fetch the data from the API using yourApiService
            val response = picturesApi.getSearch(query , currentPage.toLong())


            // Extract the data from the API response
            val items = response.pictures.distinct()


            // Calculate the next page key (if available)
            val nextPage = if (items.isEmpty()) {
                null
            } else {
                currentPage + 1
            }

            // Return the data as a LoadResult, indicating success
            return LoadResult.Page(
                data = items,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            // Handle errors and return an error result
            return LoadResult.Error(e)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, PictureModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}