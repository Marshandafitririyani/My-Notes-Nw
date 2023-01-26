package com.example.mynotes.home.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.crocodic.core.base.adapter.CoreListAdapter
import com.example.mynotes.Base.Fragment
import com.example.mynotes.R
import com.example.mynotes.add.AddNoteActivity
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.CustomeCardBinding
import com.example.mynotes.databinding.FragmentHomeBinding
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : Fragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val note = ArrayList<Note?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rcRecyclerView?.adapter =
            CoreListAdapter<CustomeCardBinding, Note>(R.layout.custome_card)
                .initItem(note)

        Timber.d("cek")


        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()

        addDummyData()

    }

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
}

//        binding?.fragment = this
//
//        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//
//        binding?.rcRecyclerView?.layoutManager = layoutManager
//        binding?.rcRecyclerView?.hasFixedSize()
//        layoutManager.reverseLayout = true
//        layoutManager.stackFromEnd = true
//        val listNote= iterator<> {  }.takeLast(5)
//        binding?.rcRecyclerView?.adapter = (listNote) {
//
//        }
//
//        fun addNotes() {
//            startActivity(Intent(requireContext(), AddNoteActivity::class.java))
//        }
//
//        fun search() {
//            startActivity(Intent(requireContext(), HomeFragment::class.java))
//        }




