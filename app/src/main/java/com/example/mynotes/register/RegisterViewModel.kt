package com.example.mynotes.register

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.toObject
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.api.ApiService
import com.example.mynotes.data.Note
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao
) :

    BaseViewModel() {
    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.register(name, email, password) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    _apiResponse.send(ApiResponse().responseSuccess())
                }

            })
    }
}


