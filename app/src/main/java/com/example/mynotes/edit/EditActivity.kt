package com.example.mynotes.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityEditBinding
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.home.HomeActivity
import com.example.mynotes.home.HomeViewModel
import com.example.mynotes.profil.ProfilActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditActivity : BaseActivity<ActivityEditBinding, EditViewModel>(R.layout.activity_edit){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.back.setOnClickListener {

            openActivity<ProfilActivity>(){
            }
        }
        binding.btnSaveProfil.setOnClickListener {
            if (binding.etNameProfil.isEmptyRequired(R.string.label_must_fill) || binding.etNameEmail.isEmptyRequired(R.string.label_must_fill)) {
                return@setOnClickListener
            }
            val name = binding.etNameProfil.textOf()
            val email = binding.etNameEmail.textOf()
//            val photo = binding.EditPhoto.()

            viewModel.updateProfile(name,email)
        }

//        setContentView(R.layout.activity_edit)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect{
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Loggin in")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<ProfilActivity>()
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
