package com.example.mynotes.edit

import android.provider.ContactsContract.Contacts.Photo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toList
import com.crocodic.core.extension.toObject
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.R
import com.example.mynotes.api.ApiService
import com.example.mynotes.const.Const
import com.example.mynotes.data.Note
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.example.mynotes.databinding.ActivityEditBinding
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.home.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val apiService: ApiService,
    private val session: CoreSession,
    private val gson: Gson,
    private val userDao: UserDao
) :

    BaseViewModel() {

    //untuk refresh
    var isRefresh = MutableLiveData<Int>()

    //untuk update profil untuk nama
    fun updateProfile(name: String) = viewModelScope.launch {
        println("Nama: $name")
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.updateProfile(name) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    userDao.insert(data.copy(idRoom = 1))
                    _apiResponse.send(ApiResponse().responseSuccess("profile updated"))

                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    refresfhToken(1)
                    _apiResponse.send(ApiResponse().responseError())
                }
            })
    }

    //untuk update profil photo
    fun updateProfileWithPhoto(name: String, photo: File) = viewModelScope.launch {
        println("Nama: $name")
        val fileBody = photo.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("photo", photo.name, fileBody)
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.updateProfileWithPhoto(name, filePart) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    userDao.insert(data.copy(idRoom = 1))
                    _apiResponse.send(ApiResponse().responseSuccess("profile updated"))

                }

                override suspend fun onError(response: ApiResponse) {
                    super.onError(response)
                    refresfhToken(2)
                    _apiResponse.send(ApiResponse().responseError())
                }
            })
    }


    private fun refresfhToken(type: Int) {
        //0 -> gagal
        //1 -> updateProfile
        //2 -> updateProfileWithPhoto
        viewModelScope.launch {
            _apiResponse.send(ApiResponse().responseLoading())
            ApiObserver(
                { apiService.getRenewToken() },
                false,
                object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {
                        val newToken = response.getString("token")
                        //session untuk menyimpan token supaya noetnya tidak hilang
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

