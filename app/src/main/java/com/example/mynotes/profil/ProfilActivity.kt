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

    }
}

