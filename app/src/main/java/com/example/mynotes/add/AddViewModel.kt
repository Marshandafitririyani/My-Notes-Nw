package com.example.mynotes.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.api.ApiService
import com.example.mynotes.const.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val apiService: ApiService,
    private val session: CoreSession
) : BaseViewModel() {

    var isRefresh = MutableLiveData<Int>()

    fun createNote(title: String, content: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.createNote(title, content) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess())
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    refresfhToken(4)
                    _apiResponse.send(ApiResponse().responseError())
                }
            })
    }

    fun updateNote(id: String, title: String, content: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.updateNote(id, title, content) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess())
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    refresfhToken(5)
                    _apiResponse.send(ApiResponse().responseError())
                }
            })
    }

    fun deletNote(id: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.deletNote(id) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess())
                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    refresfhToken(6)
                    _apiResponse.send(ApiResponse().responseError())
                }
            })
    }


    private fun refresfhToken(type: Int) {
        viewModelScope.launch {
            _apiResponse.send(ApiResponse().responseLoading())
            ApiObserver(
                { apiService.getRenewToken() },
                false,
                object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {
                        val newToken = response.getString("token")
                        session.setValue(Const.TOKEN.API_TOKEN, newToken)
                        isRefresh.postValue(type)
                    }

                    override suspend fun onError(response: ApiResponse) {
                        isRefresh.postValue(0)
                    }

                })
        }
    }
}




