package com.example.mynotes.profil

import androidx.lifecycle.viewModelScope
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.api.ApiService
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.nio.channels.Channel
import javax.inject.Inject

@HiltViewModel
class ProfilViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao
) : BaseViewModel() {

    private val _user = kotlinx.coroutines.channels.Channel<List<User>>()
    val user = _user.receiveAsFlow()

    val getUser = userDao.getUser()

    fun logout(logout: () -> Unit) = viewModelScope.launch {
        logout()
        logoutSuccess()
    }
}

