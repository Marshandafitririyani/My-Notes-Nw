package com.example.mynotes.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.add.AddNoteActivity
import com.example.mynotes.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding.btnAddHome.setOnClickListener {
            openActivity<AddNoteActivity> {
                finish()
            }
        }
    }
}

