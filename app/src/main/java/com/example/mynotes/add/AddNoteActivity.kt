package com.example.mynotes.add

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
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityAddNotesBinding
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteActivity : BaseActivity<ActivityAddNotesBinding,AddViewModel>(R.layout.activity_add_notes) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivKembali.setOnClickListener {
           finish()

            if(binding.etAddTitle.isEmptyRequired(R.string.label_must_fill) || binding.etAddCon.isEmptyRequired(R.string.label_must_fill)){
                return@setOnClickListener
            }
            val title = binding.etAddTitle.textOf()
            val content = binding.etAddCon.textOf()

            viewModel.createNote(title,content)

        }
        binding.btnSaveAdd.setOnClickListener {
            if(binding.etAddTitle.isEmptyRequired(R.string.label_must_fill) || binding.etAddCon.isEmptyRequired(R.string.label_must_fill)){
                return@setOnClickListener
            }
            val title = binding.etAddTitle.textOf()
            val content = binding.etAddCon.textOf()

            viewModel.createNote(title,content)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect{
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Loggin in")
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

    override fun onBackPressed() {

    }
}
