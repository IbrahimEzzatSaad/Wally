package com.example.wally.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.domain.usecases.GetFavorite
import com.example.wally.domain.usecases.UpdateFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavorite: GetFavorite,
    private val updateFavorite: UpdateFavorite,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _favorite = MutableStateFlow<List<PicturesItem>?>(null)
    val favorite: StateFlow<List<PicturesItem>?> =
        _favorite.asStateFlow()


    init{
        viewModelScope.launch(mainDispatcher) {
            getFavorite().let{
                _favorite.emit(it)
            }
        }
    }

    fun updateFavorites(id : String){
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(id)
        }
    }

}