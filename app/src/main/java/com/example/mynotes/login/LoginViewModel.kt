package com.example.mynotes.login

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.api.ApiService
import com.example.mynotes.const.Const
import com.example.mynotes.data.Note
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession

) : BaseViewModel() {
    //memberi
    fun login(email: String, password: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.login(email, password) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    val newToken = response.getString("token")
                    session.setValue(Const.TOKEN.API_TOKEN, newToken)
                    userDao.insert(data.copy(idRoom = 1))
                    _apiResponse.send(ApiResponse().responseSuccess())
                    session.setValue(Const.USER.EMAIL, email)
                    session.setValue(Const.USER.PASSWORD, password)
                    session.setValue(Const.USER.PROFILE, "Login")
                    println("Token: $newToken")
                }
            })
    }

    //token
    fun getToken() {
        viewModelScope.launch {
            ApiObserver(
                block = { apiService.getToken() },
                toast = false,
                responseListener = object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {

                        //Timber untuk mengecek
                        Timber.d("CekResponToken: $response")

                        val token = response.getString("token")
                        session.setValue(Const.TOKEN.API_TOKEN, token)
                    }

                    override suspend fun onError(response: ApiResponse) {
                        super.onError(response)

                    }
                }
            )
        }
    }
}

