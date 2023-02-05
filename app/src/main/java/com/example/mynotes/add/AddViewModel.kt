package com.example.mynotes.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.api.ApiService
import com.example.mynotes.const.Const
import com.example.mynotes.data.Note
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


    //untuk membuat note
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

    //untuk memperbarui note
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

    //untuk menghapus note
    fun deletNote(id: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            {apiService.deletNote(id)},
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

    //untuk refresh token
    private fun refresfhToken(type: Int) {
        //0 -> gagal
        //4 -> creatnote
        //5 -> updateNote
        //6 -> deletNote
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

/*
private fun refresfhToken(type: Int){
    //0 -> gagal
    //1 -> updateProfile
    //2 -> updateProfileWithPhoto
    viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({apiService.getRenewToken()}, false, object : ApiObserver.ResponseListener{
            override suspend fun  onSuccess(response: JSONObject) {
                isRefresh.postValue(type)
            }
            override suspend fun onError(response: ApiResponse) {
                isRefresh.postValue(0)
            }
        })
    }

}
*/


