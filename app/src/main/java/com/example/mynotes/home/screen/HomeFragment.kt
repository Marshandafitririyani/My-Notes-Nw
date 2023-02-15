package com.example.mynotes.home.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
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

        binding?.searchhome?.doOnTextChanged { text, start, before, count ->
            if (note.isEmpty()) {
                activity?.tos("kosong")
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

        binding?.rcRecyclerView?.adapter =
            CoreListAdapter<CustomeCardBinding, Note>(R.layout.custome_card)
                .initItem(note) { position, data ->
                    activity?.openActivity<AddNoteActivity> {
                        putExtra(Const.NOTE.NOTE, data)
                    }
                }


        Timber.d("cek")

        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()

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

            if (note.isEmpty()) {
                binding?.tvKosong?.visibility = View.VISIBLE
            } else {
                binding?.tvKosong?.visibility = View.GONE
            }
            Timber.d("cek data note $it")
            noteAll.clear()
            noteAll.addAll(it)
        }

        viewmodel.getNote()
    }
}





