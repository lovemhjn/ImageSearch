package com.app.imagesearch.data.remote.api

import androidx.lifecycle.LiveData
import com.app.imagesearch.constants.ServerConstants
import com.app.imagesearch.data.remote.model.ImageSearchResponse
import com.app.imagesearch.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ServerConstants.SEARCH)
    fun searchImages(
        @Header("Authorization") authToken: String,
        @Path("pageNo") pageNo: Int, @Query("q") searchTerm: String ): LiveData<GenericApiResponse<ImageSearchResponse>>
}