package com.example.mynotes.Splash

import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.login.LoginActivity
import com.example.mynotes.R
import com.example.mynotes.const.Const
import com.example.mynotes.databinding.ActivitySplashBinding
import com.example.mynotes.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewActivity>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //supaya tidak login ulang
        val userLogin = session.getString(Const.USER.PROFILE)

        viewModel.splash {
            if (userLogin == "Login") {
                openActivity<HomeActivity>()
                finish()
            } else {
                openActivity<LoginActivity>()
                finish()
            }
        }
    }
}
