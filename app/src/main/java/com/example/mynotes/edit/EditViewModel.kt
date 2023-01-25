package com.example.mynotes.edit

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.R
import com.example.mynotes.api.ApiService
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.ActivityEditBinding
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.home.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class EditViewModel  @Inject constructor(private val apiService: ApiService, private val gson: Gson, private val userDao: UserDao): BaseViewModel() {
    fun  updateProfile(name: String,email: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({apiService. updateProfile(name,email)}, false, object : ApiObserver.ResponseListener{
            override suspend fun  onSuccess(response: JSONObject) {
//                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
//                    userDao.insert(data.copy(idRoom = 1))
                _apiResponse.send(ApiResponse().responseSuccess())
//                    val message = response.getString(ApiCode.MESSAGE)
//                    _apiResponse.send(ApiResponse(status = ApiStatus.ERROR, message = message ))
            }

        })
    }
}
