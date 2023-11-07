package com.example.wally.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wally.data.api.model.PicturesItem
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
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
    val search: Flow<PagingData<PicturesItem>> =
        searchQuery.debounce(2000L) // Wait for 2 seconds of inactivity
            .distinctUntilChanged()
            .flatMapLatest { query ->
                Log.i("Tracking: ", "??"+ query)

                if (query.isNotEmpty()) {
                    querySearch(query)
                } else {
                    flowOf(PagingData.empty()) // Empty result for an empty query
                }
            }
            .flowOn(mainDispatcher)


    fun search(query: String){
        viewModelScope.launch(mainDispatcher) {
            _searchQuery.emit(query)

        }
    }


    fun updateFavorites(item : PicturesItem){
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(item)
        }
    }

}