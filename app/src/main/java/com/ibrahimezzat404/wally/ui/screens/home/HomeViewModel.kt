package com.ibrahimezzat404.wally.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.domain.usecases.FetchFeatured
import com.ibrahimezzat404.wally.domain.usecases.GetFavorite
import com.ibrahimezzat404.wally.domain.usecases.GetFeatured
import com.ibrahimezzat404.wally.domain.usecases.GetPictures
import com.ibrahimezzat404.wally.domain.usecases.UpdateFavorite
import com.ibrahimezzat404.wally.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPictures: GetPictures,
    private val getFeatured: GetFeatured,
    private val fetchFeatured: FetchFeatured,
    private val updateFavorite: UpdateFavorite,
    private val getFavorite: GetFavorite,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _pictures: MutableStateFlow<PagingData<PictureModel>> =
        MutableStateFlow(value = PagingData.empty())
    val pictures: StateFlow<PagingData<PictureModel>> =
        _pictures.asStateFlow()


    private val _featured = MutableStateFlow<List<PictureModel>?>(null)
    val featured: StateFlow<List<PictureModel>?> =
        _featured.asStateFlow()

    private val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites: StateFlow<List<String>> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    init {
        viewModelScope.launch(mainDispatcher) {
            try {
                onNewUiState(true)
                fetchFeatured()
                subscribeToFeaturedUpdates()
                subscribeToPicturesUpdates()
                getFavorite().collect {
                    _favorites.emit(it.map { it.id })
                }
            } catch (e: IOException) {
                e.message?.let { Logger.e(t = e, message = it) }
                onNewUiState(false)
                subscribeToFeaturedUpdates()
                subscribeToPicturesUpdates()

            }
        }
    }


    private fun subscribeToPicturesUpdates() {
        viewModelScope.launch(mainDispatcher) {
            getPictures().distinctUntilChanged().cachedIn(viewModelScope).collect {
                onNewPicturesList(it)

            }
        }
    }

    private suspend fun onNewPicturesList(pictures: PagingData<PictureModel>) {
        _pictures.emit(pictures)
    }

    private fun subscribeToFeaturedUpdates() {
        viewModelScope.launch(mainDispatcher) {
            getFeatured().distinctUntilChanged().collect {
                onNewFeaturedList(it)

            }
        }
    }

    private suspend fun onNewFeaturedList(featured: List<PictureModel>) {
        if (this.featured.value == null){
            _featured.emit(featured)
        }else if(this.featured.value != featured && featured.size == 10) {
            _featured.emit(featured)
        }
        onNewUiState(false)
    }


    fun updateFavorites(item: PictureModel) {
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(item)
        }
    }


    private suspend fun onNewUiState(loading: Boolean) {
        _isLoading.emit(loading)
    }

    fun retry() {
        viewModelScope.launch(mainDispatcher) {
            try {
                onNewUiState(true)
                fetchFeatured()
            } catch (e: IOException) {
                onNewUiState(loading = false)
            }
        }
    }
}