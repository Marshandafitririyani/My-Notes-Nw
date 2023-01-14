package com.example.mynotes.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.crocodic.core.extension.openActivity
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.login.LoginActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBack.setOnClickListener {
            openActivity<LoginActivity>(){
                finish()
            }
        }
    }
}
