package com.app.imagesearch.ui.search.state

sealed class SearchStateEvent {

    class SearchImageEvent(val searchTerm:String): SearchStateEvent()
    class NextPageEvent(val searchTerm:String): SearchStateEvent()

    object None : SearchStateEvent()
}