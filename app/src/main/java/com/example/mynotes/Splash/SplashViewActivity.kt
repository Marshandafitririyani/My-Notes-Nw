package com.example.mynotes.Splash

import androidx.lifecycle.viewModelScope
import com.example.mynotes.Base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewActivity @Inject constructor() : BaseViewModel() {
    fun splash(done: () -> Unit) = viewModelScope.launch {
        delay(3000)
        done()
    }
}
