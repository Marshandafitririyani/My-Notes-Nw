package com.example.mynotes.home.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.crocodic.core.extension.openActivity
import com.example.mynotes.Base.Fragment
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentProfilBinding
import com.example.mynotes.edit.EditActivity
import com.example.mynotes.home.HomeActivity


class ProfilFragment : Fragment<FragmentProfilBinding>(R.layout.fragment_profil) {

    private lateinit var buttonKembali : ImageView
    private lateinit var buttonLoguot : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonKembali = view.findViewById(R.id.rectangleProfil)
        buttonLoguot = view.findViewById((R.id.rectangleLogout))


        buttonKembali.setOnClickListener {
            val kembali = Intent(requireContext(), EditActivity::class.java)
            startActivity(kembali)
        }

        buttonLoguot.setOnClickListener {
            val logout = Intent(requireContext(), EditActivity::class.java)
            startActivity(logout)
        }


    }

}