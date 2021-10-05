package com.mvvm.kotlin.data.api

import com.mvvm.kotlin.data.repository.MatchesRepositories
import com.mvvm.kotlin.model.MatchesResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getMatches() : Response<MatchesResponse>
}