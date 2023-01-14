package com.example.mynotes.Splash

import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.login.LoginActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewActivity>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.splash {
                openActivity<LoginActivity>()
            finish()
        }
    }
}
