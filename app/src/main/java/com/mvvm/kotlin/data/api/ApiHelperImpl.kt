package com.mvvm.kotlin.data.api

import com.mvvm.kotlin.data.repository.MatchesRepositories
import com.mvvm.kotlin.model.MatchesResponse
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper{

    override suspend fun getMatches() : Response<MatchesResponse> = apiService.getMatches()

}