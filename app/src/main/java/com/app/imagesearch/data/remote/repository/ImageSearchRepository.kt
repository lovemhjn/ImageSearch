package com.app.imagesearch.data.remote.repository

import androidx.lifecycle.LiveData
import com.app.imagesearch.constants.AppConstants
import com.app.imagesearch.data.remote.api.ApiService
import com.app.imagesearch.data.remote.model.ImageSearchResponse
import com.app.imagesearch.ui.search.state.SearchViewState
import com.app.imagesearch.util.ApiSuccessResponse
import com.app.imagesearch.util.DataState
import com.app.imagesearch.util.GenericApiResponse
import javax.inject.Inject

class ImageSearchRepository @Inject constructor(val apiService: ApiService) {

    fun getNews(pageNO: Int, searchTerm: String): LiveData<DataState<SearchViewState>> {
        return object : NetworkBoundResource<ImageSearchResponse, SearchViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ImageSearchResponse>) {
                result.value = DataState.data(
                    null,
                    SearchViewState(
                        searchResponse = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<ImageSearchResponse>> {
                return apiService.searchImages(AppConstants.TOKEN, pageNO, searchTerm)
            }

        }.asLiveData()
    }

    fun getNextPage(pageNO: Int, searchTerm: String): LiveData<DataState<SearchViewState>> {
        return object : NetworkBoundResource<ImageSearchResponse, SearchViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ImageSearchResponse>) {
                result.value = DataState.data(
                    null,
                    SearchViewState(
                         nextPageResponse = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<ImageSearchResponse>> {
                return apiService.searchImages(AppConstants.TOKEN, pageNO, searchTerm)
            }

        }.asLiveData()
    }
}