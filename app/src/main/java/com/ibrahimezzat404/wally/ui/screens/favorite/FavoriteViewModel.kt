package com.ibrahimezzat404.wally.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.domain.usecases.GetFavorite
import com.ibrahimezzat404.wally.domain.usecases.UpdateFavorite
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

    private val _favorite = MutableStateFlow<List<PictureModel>?>(null)
    val favorite: StateFlow<List<PictureModel>?> =
        _favorite.asStateFlow()


    init{
        viewModelScope.launch(mainDispatcher) {
            getFavorite().collect {
                _favorite.emit(it)
            }
        }
    }

    fun updateFavorites(item : PictureModel){
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(item)
        }
    }

}