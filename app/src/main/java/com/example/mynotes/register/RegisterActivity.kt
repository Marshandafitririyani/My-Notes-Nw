package com.example.mynotes.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.login.LoginActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityRegisterBinding
import com.example.mynotes.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //kembali ke logogin
        binding.ivBack.setOnClickListener {
            tos("You are not register?")
            openActivity<LoginActivity>(){
                finish()
            }
        }
        binding.btnSaveRegister.setOnClickListener {
             if(binding.etNameRegister.isEmptyRequired(R.string.label_must_fill) ||
                binding.etEmailRegister.isEmptyRequired(R.string.label_must_fill) ||
                binding.etPasswordRegister.isEmptyRequired(R.string.label_must_fill) ||
                binding.etConPasRegister.isEmptyRequired(R.string.label_must_fill)){
                return@setOnClickListener
            }
            val name = binding.etNameRegister.textOf()
            val email = binding.etEmailRegister.textOf()
            val password = binding.etPasswordRegister.textOf()

            viewModel.register(name,email,password,)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect{
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Please Wait Register")
                            ApiStatus.SUCCESS -> { tos("Please Login")
                                loadingDialog.show("Succes")
                                openActivity<LoginActivity>()
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
