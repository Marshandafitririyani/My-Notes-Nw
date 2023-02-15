package com.example.mynotes.profil

import androidx.lifecycle.viewModelScope
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.data.User
import com.example.mynotes.data.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilViewModel @Inject constructor(

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

