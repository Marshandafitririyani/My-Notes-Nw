package com.example.mynotes.home.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crocodic.core.extension.tos
import com.example.mynotes.Base.Fragment
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentProfilBinding
import com.example.mynotes.edit.EditActivity
import com.example.mynotes.profil.ProfilViewModel
import kotlinx.coroutines.launch


class ProfilFragment : Fragment<FragmentProfilBinding>(R.layout.fragment_profil) {

    private lateinit var buttonEdit: ImageView
    private lateinit var buttonLogout: ImageView

    private val viewmodel by activityViewModels<ProfilViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonEdit = view.findViewById(R.id.rectangleProfil)
        buttonLogout = view.findViewById((R.id.rectangleLogout))


        buttonEdit.setOnClickListener {
            val kembali = Intent(requireContext(), EditActivity::class.java).apply {
                putExtra("username", binding?.user?.name)
            }
            startActivity(kembali)
        }


        buttonLogout.setOnClickListener {
            viewmodel.logout { activity?.tos("Logout") }
        }


        lifecycleScope.launch {
            viewmodel.getUser.observe(requireActivity()) {
                it?.let { data ->
                    binding?.user = data
                    binding?.let { viewImage ->
                        Glide
                            .with(requireContext())
                            .load(it.photo)
                            .placeholder(R.drawable.picture)
                            .error(R.drawable.error)
                            .apply(RequestOptions.centerInsideTransform())
                            .into(viewImage.ivImage)
                    }
                }
            }
        }

    }

}