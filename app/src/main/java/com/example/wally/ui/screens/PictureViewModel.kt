package com.example.wally.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.domain.usecases.FetchFeatured
import com.example.wally.domain.usecases.GetFavorite
import com.example.wally.domain.usecases.GetFeatured
import com.example.wally.domain.usecases.GetPictures
import com.example.wally.domain.usecases.UpdateFavorite
import com.example.wally.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val getPictures: GetPictures,
    private val getFeatured: GetFeatured,
    private val fetchFeatured: FetchFeatured,
    private val updateFavorite: UpdateFavorite,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _pictures: MutableStateFlow<PagingData<PicturesItem>> =
        MutableStateFlow(value = PagingData.empty())
    val pictures: StateFlow<PagingData<PicturesItem>> =
        _pictures.asStateFlow()


    private val _featured = MutableStateFlow<List<PicturesItem>?>(null)
    val featured: StateFlow<List<PicturesItem>?> =
        _featured.asStateFlow()



    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch(mainDispatcher) {
            try {
                onNewUiState(true, "")
                fetchFeatured()
                subscribeToFeaturedUpdates()
                subscribeToPicturesUpdates()

            } catch (e: IOException) {
                e.message?.let { onNewUiState(false, it) }
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

    private suspend fun onNewPicturesList(pictures: PagingData<PicturesItem>) {
        _pictures.emit(pictures)
    }

    private fun subscribeToFeaturedUpdates() {
        viewModelScope.launch(mainDispatcher) {
            getFeatured().distinctUntilChanged().collect {
                onNewFeaturedList(it)
            }
        }
    }

    private suspend fun onNewFeaturedList(pictures: List<PicturesItem>) {
        _featured.emit(pictures)

        if (pictures.isNotEmpty())
            onNewUiState(false, "")
        else
            onNewUiState(false, uiState.value.errorMessage)

    }


    fun updateFavorites(id : String){
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(id)
        }
    }


    private fun onNewUiState(loading: Boolean, error: String) {
        if (_uiState.value != UiState(loading, error)) {
            _uiState.update { currentUiState ->
                currentUiState.copy(isLoading = loading, errorMessage = error)
            }
        }
    }

    fun retry() {
        viewModelScope.launch(mainDispatcher) {
            try {
                onNewUiState(true, uiState.value.errorMessage)
                fetchFeatured()
            } catch (e: IOException) {
                e.message?.let { onNewUiState(loading = false, error = it) }
            }

        }
    }
}