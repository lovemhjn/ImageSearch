package com.app.imagesearch.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.imagesearch.data.remote.model.ImageSearchResponse
import com.app.imagesearch.data.remote.repository.ImageSearchRepository
import com.app.imagesearch.ui.search.state.SearchStateEvent
import com.app.imagesearch.ui.search.state.SearchViewState
import com.app.imagesearch.util.AbsentLiveData
import com.app.imagesearch.util.DataState

class ImageSearchViewModel @ViewModelInject constructor(private val repository: ImageSearchRepository): ViewModel() {
    private val _stateEvent: MutableLiveData<SearchStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<SearchViewState> = MutableLiveData()
    private var pageNo = 1

    val viewState: LiveData<SearchViewState>
        get() = _viewState


    val dataState: LiveData<DataState<SearchViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun handleStateEvent(stateEvent: SearchStateEvent): LiveData<DataState<SearchViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        return when (stateEvent) {

            is SearchStateEvent.SearchImageEvent -> {
                pageNo = 1
                repository.getNews(pageNo, stateEvent.searchTerm)
            }

            is SearchStateEvent.NextPageEvent -> {
                repository.getNextPage(++pageNo,stateEvent.searchTerm)
            }

            is SearchStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setSummaryData(news: ImageSearchResponse) {
        val update = getCurrentViewStateOrNew()
        update.searchResponse = news
        _viewState.value = update
    }

    fun setNextPageData(news: ImageSearchResponse) {
        val update = getCurrentViewStateOrNew()
        update.nextPageResponse = news
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): SearchViewState {
        return viewState.value?.let {
            it
        } ?: SearchViewState()
    }

    fun setStateEvent(event: SearchStateEvent) {
        val state: SearchStateEvent = event
        _stateEvent.value = state
    }
}