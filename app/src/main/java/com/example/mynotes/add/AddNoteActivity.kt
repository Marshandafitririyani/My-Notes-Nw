package com.example.mynotes.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
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
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.ActivityAddNotesBinding
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteActivity :
    BaseActivity<ActivityAddNotesBinding, AddViewModel>(R.layout.activity_add_notes) {

    private var note: Note? = null
    private var oldTitle: String? = null
    private var oldContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //getparcelabl untuk getExstra
        note = intent.getParcelableExtra(Const.NOTE.NOTE)

        oldTitle = note?.title
        oldTitle = note?.note

        binding.nameNote = note

        initClick()
        observe()
        /*fun edit() {
            try {
                val intent = Intent(this, AddNoteActivity::class.java).apply {
                    putExtra("noteContent", intent.getIntExtra("noteContent", 0))
                    putExtra("noteId", intent.getStringExtra("noteId").toString())
                    putExtra("noteTitle", intent.getStringExtra("noteTitle").toString())

                }
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                val intent = Intent(this, AddNoteActivity::class.java).apply {
                    putExtra("noteContent", intent.getIntExtra("noteContent", 0))
                    putExtra("noteId", intent.getStringExtra("noteId").toString())
                    putExtra("noteTitle", intent.getStringExtra("noteTitle").toString())

                }
                startActivity(intent)
                finish()
                Toast.makeText(
                    this,
                    "Gambar mungkin tidak akan muncul, karena ukurannya yang besar. Silahkan edit kembali",
                    Toast.LENGTH_LONG
                ).show()
            }
        }*/

    }

    private fun initClick() {
        //tombol back juga bisa untuk menyimpan note yg telah dibuat
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

        //untuk save note
        binding.btnSaveAdd.setOnClickListener {
            val title = binding.etAddTitle.textOf()
            val content = binding.etAddCon.textOf()

            if (binding.etAddTitle.isEmptyRequired(R.string.label_must_fill) || binding.etAddCon.isEmptyRequired(
                    R.string.label_must_fill
                )
            ) {
                return@setOnClickListener
            }

            //untuk membuat note
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

        //untuk delet note
        binding.btnDelet.setOnClickListener {
            if (note != null) {
                viewModel.deletNote(note!!.id)
            }

        }
    }



    private fun observe() {
        //refresh token
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
                //respon
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
