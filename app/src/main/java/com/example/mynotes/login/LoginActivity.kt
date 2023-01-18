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
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.const.Const
import com.example.mynotes.register.RegisterActivity
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        //developer@crocodic.comdele

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("login")
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
}
private fun tokenApi() {
    val current = if (build.VERSION.SDK_INT >= Build.VERSION_CODES .0){
    localDateTime.now()
}else {
    TODO("VERSION.SDK_INT < 0")

}
val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD")
val date: String = current.format(dateFormatter)
val token = "$date|MjAyMi0xMS0wNXxyYWhhc2lh"
val tokenEncrypt =token.base64encode()
    session.setValue(Const.TOKEN.API_TOKEN, tokenEncrypt)

    timber.d("Cek Token : $token")

    lifecycleScope.launch {
        viewModel.getToken()
    }
}


