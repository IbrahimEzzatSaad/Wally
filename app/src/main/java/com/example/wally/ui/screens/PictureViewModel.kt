package com.example.wally.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.domain.usecases.FetchPics
import com.example.wally.domain.usecases.GetPictures
import com.organization.nytimes.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel  @Inject constructor(
    private val getPictures: GetPictures,
    private val fetchPics: FetchPics,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(){

    private val _pictures = MutableStateFlow<List<PicturesItem>?>(null)
    val pictures: StateFlow<List<PicturesItem>?> =
        _pictures.asStateFlow()



    init {
        viewModelScope.launch(mainDispatcher) {
            fetchPics("1")
            subscribeToPicturesUpdates()
        }
    }


    fun subscribeToPicturesUpdates() {
        viewModelScope.launch(mainDispatcher) {
            getPictures().collect {
                    onNewPicturesList(it)
                }
        }
    }
    
    private suspend fun onNewPicturesList(pictures: List<PicturesItem>) {
        Logger.d("Got more Pictures $pictures")
        _pictures.emit(pictures)
    }

}