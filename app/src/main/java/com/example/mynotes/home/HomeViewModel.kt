package com.example.mynotes.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toList
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.api.ApiService
import com.example.mynotes.const.Const
import com.example.mynotes.data.Note
import com.example.mynotes.data.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val session: CoreSession
) : BaseViewModel() {

    val dataNote = MutableLiveData<List<Note>>()
    var isRefresh = MutableLiveData<Int>()

    fun getNote() = viewModelScope.launch {
        ApiObserver({
            apiService.note()
        }, false, object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                val data =
                    response.getJSONArray(ApiCode.DATA).toList<Note>(gson)
                dataNote.postValue(data)
                Timber.d("cek api ${data.size}")
            }

            override suspend fun onError(response: ApiResponse) {
                refresfhToken(1)
            }
        })
    }


    private fun refresfhToken(type: Int) {
        println("refresh token 1")
        viewModelScope.launch {
            println("refresh token 2")
            ApiObserver(
                { apiService.getRenewToken() },
                false,
                object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {
                        val newToken = response.getString("token")
                        println("refresh token 3.1")
                        session.setValue(Const.TOKEN.API_TOKEN, newToken)
                        isRefresh.postValue(type)
                    }

                    override suspend fun onError(response: ApiResponse) {
                        println("refresh token 3.2")
                        isRefresh.postValue(0)
                    }
                })
        }

    }
}
