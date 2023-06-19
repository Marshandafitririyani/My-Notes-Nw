package com.example.mynotes.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.base64encrypt
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.helper.DateTimeHelper
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.const.Const
import com.example.mynotes.register.RegisterActivity
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenApi()

        //untuk masuk ke activity register
        binding.btnRegister.setOnClickListener {
            tokenApi()
            openActivity<RegisterActivity>()
        }

        //menyimpan login
        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.isEmptyRequired(R.string.label_must_fill) || binding.etPassword.isEmptyRequired(
                    R.string.label_must_fill
                )
            ) {
                return@setOnClickListener
            }
            val email = binding.etEmail.textOf()
            val password = binding.etPassword.textOf()

            viewModel.login(email, password)
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show(" Please Wait login")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }

    }

    //API token dipangil disini
    private fun tokenApi() {
        val dateNow = DateTimeHelper().dateNow()
        val tokeInit = "$dateNow|rahasia"
        val tokenEncrypt = tokeInit.base64encrypt()
        CoreSession(this).setValue(Const.TOKEN.API_TOKEN, tokenEncrypt)
        Timber.d("Cek Token : $tokeInit")
        lifecycleScope.launch {
            viewModel.getToken()
        }
    }
}




