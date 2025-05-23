package com.ibrahimezzat404.wally.ui.screens.categorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.domain.usecases.GetCategoryList
import com.ibrahimezzat404.wally.domain.usecases.GetFavorite
import com.ibrahimezzat404.wally.domain.usecases.UpdateFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryList: GetCategoryList,
    private val getFavorite: GetFavorite,
    private val updateFavorite: UpdateFavorite,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    private val _category: MutableStateFlow<PagingData<PictureModel>> =
        MutableStateFlow(value = PagingData.empty())
    val category: StateFlow<PagingData<PictureModel>> =
        _category.asStateFlow()


    private val _id = MutableStateFlow<String?>(null)
    val id: StateFlow<String?> =
        _id.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites: StateFlow<List<String>> = _favorites.asStateFlow()

    init {
        viewModelScope.launch(mainDispatcher) {
            getFavorite().collect {
                _favorites.emit(it.map { it.id })
            }
        }
    }

    fun startSubscribe(id: String){
        viewModelScope.launch(mainDispatcher) {
            _id.emit(id)
            _category.emit(PagingData.empty())
            _isLoading.emit(true)
            subscribeToCategoryUpdates(id)
        }
    }

    private fun subscribeToCategoryUpdates(id: String) {
        viewModelScope.launch(mainDispatcher) {
            getCategoryList(id).distinctUntilChanged().cachedIn(viewModelScope).collect {
                onNewCategoryList(it)
            }
        }
    }

    private suspend fun onNewCategoryList(pictures: PagingData<PictureModel>) {
        _category.emit(pictures)
        _isLoading.emit(false)
    }

    fun loadingUpdate(){
        viewModelScope.launch(mainDispatcher) {
            _isLoading.emit(true)
        }
    }

    fun updateFavorites(item : PictureModel){
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(item)
        }
    }


}