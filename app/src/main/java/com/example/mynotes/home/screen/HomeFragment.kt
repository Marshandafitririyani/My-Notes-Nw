package com.example.mynotes.home.screen

import android.os.Bundle
import android.widget.Toast
import com.example.mynotes.Base.Fragment
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentHomeBinding
import timber.log.Timber


class HomeFragment : Fragment<FragmentHomeBinding>(R.layout.fragment_home) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("cek" )


        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()

    }


}