package com.example.mynotes.profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityProfilBinding
import com.example.mynotes.edit.EditActivity
import com.example.mynotes.home.HomeActivity
import com.example.mynotes.login.LoginActivity
import com.example.mynotes.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilActivity : BaseActivity<ActivityProfilBinding,ProfilViewModel>(R.layout.activity_profil) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.ivProfil.setOnClickListener {
//
//            openActivity<HomeActivity>(){
//                finish()
//            }
//        }
//        binding.rectangleProfil.setOnClickListener {
//            openActivity<EditActivity> {
//
//            }
//        }
//
//        binding.rectangleLogout.setOnClickListener {
//            viewModel.logout {
//                openActivity<LoginActivity>()
//                finish()
//            }
//
//        }
//        binding.rectangleProfil.setOnClickListener {
//            if(binding.etNameRegister.isEmptyRequired(R.string.label_must_fill) || binding.etEmailRegister.isEmptyRequired(R.string.label_must_fill) || binding.etPasswordRegister.isEmptyRequired(R.string.label_must_fill) || binding.etConPasRegister.isEmptyRequired(R.string.label_must_fill)){
//                return@setOnClickListener
//            }
//            val name = binding.etNameRegister.textOf()
//            val email = binding.etEmailRegister.textOf()
//            val password = binding.etPasswordRegister.textOf()
////            val confirm password = binding.etConPasRegister.textOf()
//
//            viewModel.register(name,email,password,)
//        }
//        setContentView(R.layout.activity_profil)

    }
}

//lass ProfileActivity : BaseActivity<ActivityProfileBinding,ProfileViewModel>(R.layout.activity_profile) {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile)
//    }
//}//