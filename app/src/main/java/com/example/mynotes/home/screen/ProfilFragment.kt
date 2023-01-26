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
import com.example.mynotes.login.LoginActivity


class ProfilFragment : Fragment<FragmentProfilBinding>(R.layout.fragment_profil) {

    private lateinit var buttonEdit : ImageView
    private lateinit var buttonLogout : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonEdit = view.findViewById(R.id.rectangleProfil)
        buttonLogout = view.findViewById((R.id.rectangleLogout))


        buttonEdit.setOnClickListener {
            val kembali = Intent(requireContext(), EditActivity::class.java)
            startActivity(kembali)
        }

        buttonLogout.setOnClickListener {
            val logout = Intent(requireContext(), LoginActivity::class.java)
            startActivity(logout)
        }


    }

}