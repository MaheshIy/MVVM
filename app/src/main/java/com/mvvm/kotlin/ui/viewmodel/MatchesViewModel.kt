package com.mvvm.kotlin.ui.viewmodel

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.kotlin.data.repository.MatchesRepositories
import com.mvvm.kotlin.model.MatchesResponse
import com.mvvm.kotlin.utils.NetworkHelper
import com.mvvm.kotlin.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MatchesViewModel(private val matchesRepositories: MatchesRepositories, private val networkHelper: NetworkHelper)
    : ViewModel() {

    private val _users = MutableLiveData<Resource<MatchesResponse>>()
    val getRepositories: LiveData<Resource<MatchesResponse>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {

            _users.postValue(Resource.loading(null))
            val isUiThread =
                if (VERSION.SDK_INT >= VERSION_CODES.M) Looper.getMainLooper().isCurrentThread else Thread.currentThread() === Looper.getMainLooper().thread

            println("Threaddd---------viewmodel"+isUiThread)

            if (networkHelper.isNetworkConnected()) {
                matchesRepositories.getMatches().let {
                    _users.postValue(Resource.success(data = it))
                }
            } else {
                matchesRepositories.loadFromDB().let {
                    _users.postValue(Resource.success(data = it))
                }
            }
        }
    }

    suspend fun updateClickedItem(id : Int, status : Int) {
            matchesRepositories.UpdateRoomList(id, status)
    }
}
