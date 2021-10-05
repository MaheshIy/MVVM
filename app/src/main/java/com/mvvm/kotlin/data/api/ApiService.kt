package com.mvvm.kotlin.data.api

import com.mvvm.kotlin.model.MatchesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/?results=10")
    suspend fun getMatches() : Response<MatchesResponse>
}