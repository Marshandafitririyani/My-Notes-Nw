package com.example.mynotes.profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityProfilBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilActivity : BaseActivity<ActivityProfilBinding,ProfilViewModel>(R.layout.activity_profil) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
    }
}

//lass ProfileActivity : BaseActivity<ActivityProfileBinding,ProfileViewModel>(R.layout.activity_profile) {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile)
//    }
//}//