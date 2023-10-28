package com.example.wally.ui.screens.categorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.domain.usecases.GetCategoryList
import com.example.wally.domain.usecases.UpdateFavorite
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
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val updateFavorite: UpdateFavorite
) : ViewModel() {


    private val _category: MutableStateFlow<PagingData<PicturesItem>> =
        MutableStateFlow(value = PagingData.empty())
    val category: StateFlow<PagingData<PicturesItem>> =
        _category.asStateFlow()


    private val _id = MutableStateFlow<String?>(null)
    val id: StateFlow<String?> =
        _id.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


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

    private suspend fun onNewCategoryList(pictures: PagingData<PicturesItem>) {
        _category.emit(pictures)
        _isLoading.emit(false)
    }

    fun loadingUpdate(){
        viewModelScope.launch(mainDispatcher) {
            _isLoading.emit(true)
        }
    }

    fun updateFavorites(item : PicturesItem){
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(item)
        }
    }


}