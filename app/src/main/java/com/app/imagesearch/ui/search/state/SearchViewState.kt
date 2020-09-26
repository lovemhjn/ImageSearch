package com.app.imagesearch.ui.search.state

import com.app.imagesearch.data.remote.model.ImageSearchResponse

data class SearchViewState(
    var searchResponse: ImageSearchResponse? = null,
    var nextPageResponse: ImageSearchResponse? = null

)