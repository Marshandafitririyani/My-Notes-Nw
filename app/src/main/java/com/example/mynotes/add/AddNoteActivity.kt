package com.example.mynotes.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityAddNotesBinding
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.home.HomeActivity

class AddNoteActivity : BaseActivity<ActivityAddNotesBinding,AddViewModel>(R.layout.activity_add_notes) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBackAdd.setOnClickListener {
            openActivity<HomeActivity>{
                finish()
            }
        }
    }
}
