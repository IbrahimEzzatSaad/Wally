package com.example.wally.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wally.data.api.model.PictureModel
import com.example.wally.domain.usecases.QuerySearch
import com.example.wally.domain.usecases.UpdateFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val updateFavorite: UpdateFavorite,
    private val querySearch: QuerySearch,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val search: Flow<PagingData<PictureModel>> =
        searchQuery.debounce(2000L)
            .flatMapLatest { query ->
                querySearch(query)
            }.flowOn(mainDispatcher)
            .cachedIn(viewModelScope)
    

    fun search(query: String) {
        viewModelScope.launch(mainDispatcher) {
            if (query != searchQuery.value && query.isNotEmpty()) {
                _searchQuery.emit(query)
            }
        }
    }


    fun updateFavorites(item: PictureModel) {
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(item)
        }
    }

}