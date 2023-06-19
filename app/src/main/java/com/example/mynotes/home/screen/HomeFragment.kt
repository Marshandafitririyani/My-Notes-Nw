package com.example.mynotes.home.screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnAttach
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.toList
import com.crocodic.core.extension.tos
import com.crocodic.core.helper.log.Log.d
import com.example.mynotes.Base.Fragment
import com.example.mynotes.R
import com.example.mynotes.add.AddNoteActivity
import com.example.mynotes.const.Const
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.CustomeCardBinding
import com.example.mynotes.databinding.FragmentHomeBinding
import com.example.mynotes.home.HomeViewModel
import timber.log.Timber


class HomeFragment : Fragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val note = ArrayList<Note?>()
    private var noteAll = ArrayList<Note?>()
    private val viewmodel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fungsi untuk filter search
        binding?.searchhome?.doOnTextChanged { text, start, before, count ->
            if (note.isEmpty()) {
                activity?.tos("Note file is empty")
            }
            if (text.isNullOrEmpty()) {
                note.clear()
                binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()
                note.addAll(noteAll)
                binding?.rcRecyclerView?.adapter?.notifyItemInserted(0)
            } else {
                val filter = noteAll?.filter { it?.title?.contains("$text", true) == true }
                note.clear()
                filter?.forEach {
                    note.add(it)
                }
                binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()
                binding?.rcRecyclerView?.adapter?.notifyItemInserted(0)
            }
        }

        /*val filter =note.filter {it?.idRoom !=1}
        binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()

        note.addAll(filter)
        noteAll.addAll(filter)

        binding?.rcRecyclerView?.adapter?.notifyItemInserted(0)
        binding?.loading = false*/

        //adapter untuk notenya
        binding?.rcRecyclerView?.adapter =
            CoreListAdapter<CustomeCardBinding, Note>(R.layout.custome_card)
                .initItem(note) { position, data ->
                    activity?.openActivity<AddNoteActivity> {
                        //putExtra untuk memunculkan isi note yang sudah dibuat saat diklik
                        putExtra(Const.NOTE.NOTE, data)
                    }
                }


//timber untuk mengecek
        Timber.d("cek")

//toast untuk memunculkan nama activity yg di klik
//        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()

//observe untuk memanggil getnotnya (sek masih lupa)
        viewmodel.isRefresh.observe(requireActivity()) {
            when (it) {
                0 -> {
                    activity?.tos("Gagal refresh token")
                }
                1 -> {
                    viewmodel.getNote()
                }
            }
        }

        viewmodel.dataNote.observe(viewLifecycleOwner) {
            note.clear()
            note.addAll(it)
            binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()

//untuk memberitahu apakah halamn homen itu kosonng
            if (note.isEmpty()) {
                binding?.tvKosong?.visibility = View.VISIBLE
            } else {
                binding?.tvKosong?.visibility = View.GONE
            }
            Timber.d("cek data note $it")
            noteAll.clear()
            noteAll.addAll(it)
        }

//memanggil getNotenya
        viewmodel.getNote()
    }
}

/*

        private var  note() {
            viewmodel.note()

        }

        addDummyData()


    private fun addDummyData() {
        val dummy = arrayOf(
            Note(1, "Test 1", "Test1234567890", "2023-01-12", 1),
            Note(2, "Test 2", "Test1234567890", "2023-01-12", 1),
            Note(3, "Test 3", "Test1234567890", "2023-01-12", 1),
            Note(4, "Test 4", "Test1234567890", "2023-01-12", 1),
            Note(5, "Test 5", "Test1234567890", "2023-01-12", 1),
            Note(6, "Test 6", "Test1234567890", "2023-01-12", 1),
            Note(7, "Test 7", "Test1234567890", "2023-01-12", 1),
            Note(8, "Test 8", "Test1234567890", "2023-01-12", 1),
            Note(9, "Test 9", "Test1234567890", "2023-01-12", 1),
        )

        note.clear()
        note.addAll(dummy)

        binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()
    }
*/


/*        binding?.fragment = this

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding?.rcRecyclerView?.layoutManager = layoutManager
        binding?.rcRecyclerView?.hasFixedSize()
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        val listNote= iterator<> {  }.takeLast(5)
        binding?.rcRecyclerView?.adapter = (listNote) {

        }

        fun addNotes() {
            startActivity(Intent(requireContext(), AddNoteActivity::class.java))
        }

        fun search() {
            startActivity(Intent(requireContext(), HomeFragment::class.java))
        }*/




