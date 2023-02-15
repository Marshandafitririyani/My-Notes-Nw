package com.example.mynotes.add


import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.helper.DateTimeHelper
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.const.Const
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.ActivityAddNotesBinding
import com.example.mynotes.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class AddNoteActivity :
    BaseActivity<ActivityAddNotesBinding, AddViewModel>(R.layout.activity_add_notes) {

    private var note: Note? = null
    private var oldTitle: String? = null
    private var oldContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        note = intent.getParcelableExtra(Const.NOTE.NOTE)

        oldTitle = note?.title
        oldTitle = note?.note

        binding.nameNote = note

        title = "KotlinApp"
        val text: TextView = findViewById(R.id.date_note)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateString =
            simpleDateFormat.format(note?.updated_At ?: DateTimeHelper().createAtLong())
        text.text = String.format("Date: %s", dateString)



        initClick()
        observe()
    }

    private fun initClick() {
        binding.ivKembali.setOnClickListener {
            val title = binding.etAddTitle.textOf()
            val content = binding.etAddCon.textOf()

            if (binding.etAddTitle.isEmptyRequired(R.string.label_must_fill) || binding.etAddCon.isEmptyRequired(
                    R.string.label_must_fill
                )
            ) {
                return@setOnClickListener
            }

            if (oldTitle.isNullOrEmpty() && oldContent.isNullOrEmpty()) {
                viewModel.createNote(title, content)
            } else {
                if (note != null) {
                    if (oldTitle != title || oldContent != content) {
                        viewModel.updateNote(note!!.id, title, content)
                    }
                }
            }

        }

        binding.btnSaveAdd.setOnClickListener {
            val title = binding.etAddTitle.textOf()
            val content = binding.etAddCon.textOf()

            if (binding.etAddTitle.isEmptyRequired(R.string.label_must_fill) || binding.etAddCon.isEmptyRequired(
                    R.string.label_must_fill
                )
            ) {
                return@setOnClickListener
            }

            if (oldTitle.isNullOrEmpty() && oldContent.isNullOrEmpty()) {
                viewModel.createNote(title, content)
            } else {
                if (note != null) {
                    //untuk mengupdate note
                    if (oldTitle != title || oldContent != content) {
                        viewModel.updateNote(note!!.id, title, content)
                    }
                }
            }
        }

        binding.btnDelet.setOnClickListener {
            if (note != null) {
                viewModel.deletNote(note!!.id)
            }

        }
    }


    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isRefresh.observe(this@AddNoteActivity) {
                        when (it) {
                            0 -> {
                                loadingDialog.setResponse("Gagal renew token")
                            }
                            4 -> {
                                val title = binding.etAddTitle.textOf()
                                val content = binding.etAddCon.textOf()
                                viewModel.createNote(title, content)
                            }
                            5 -> {
                                val id = binding.etAddTitle.textOf()
                                val title = binding.etAddTitle.textOf()
                                val content = binding.etAddCon.textOf()
                                viewModel.updateNote(id, title, content!!)
                            }
                            6 -> {
                                val id = binding.etAddTitle.textOf()
                                viewModel.deletNote(id)
                            }
                            else -> {
                                loadingDialog.setResponse("Else terkondisi")
                            }
                        }

                    }
                }
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show(" Save Note")
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
