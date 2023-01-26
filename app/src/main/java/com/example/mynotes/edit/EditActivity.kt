package com.example.mynotes.edit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityEditBinding
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.home.HomeActivity
import com.example.mynotes.home.HomeViewModel
import com.example.mynotes.home.screen.ProfilFragment
import com.example.mynotes.profil.ProfilActivity
import com.example.mynotes.profil.ProfilActivity_GeneratedInjector
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executors

@AndroidEntryPoint
class EditActivity : BaseActivity<ActivityEditBinding, EditViewModel>(R.layout.activity_edit) {

    private lateinit var photoFile: File
//    private lateinit var bitmapCamera: Bitmap
//    private lateinit var bitmapGallery: Bitmap
//
//    var idNnote = 0
//    var bitmapPhoto = ""
//    var bitmapString = ""
//    var editMode = false
//    var profileComplete = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        photoFile = getPhotoFile()

        initClick()
        observe()

    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Save Profil")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<ProfilActivity>()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }
    }

    private fun initClick() {
        binding.back.setOnClickListener {
            finish()
        }
        binding.btnSaveProfil.setOnClickListener {
            if (binding.etNameProfil.isEmptyRequired(R.string.label_must_fill) || binding.etNameEmail.isEmptyRequired(
                    R.string.label_must_fill
                )
            ) {
                return@setOnClickListener
            }
            val name = binding.etNameProfil.textOf()
            val email = binding.etNameEmail.textOf()
//            val photo = binding.EditPhoto.()

            viewModel.updateProfile(name, email)
        }
    }



    private var activityLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let { uri ->

                val bitmapImage = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(this.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }

                binding.EditPhoto.setImageBitmap(bitmapImage)
            }
        }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityLauncherGallery.launch(galleryIntent)
    }

    fun checkPermissionGallery() {
        val galleryPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        if (galleryPermission) {
            openGallery()
        } else {
            requestPermissionGallery()
        }
    }

    private fun requestPermissionGallery() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            200
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 200) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                openGallery()
            } else {
                Toast.makeText(this, "Ijin gallery ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    }

//    fun profileClick() {
//        // checkPermissionCamera()
//        checkPermissionGallery()
//    }
//
//    fun saveData() {
//        if (binding.name.toString().trim() != "null" && binding.email.toString()
//                .trim() != "null"
//        ) {
//            if (binding.name.toString().trim().isNotEmpty() && binding.email.toString().trim()
//                    .isNotEmpty()
//            ) {
//
//                if (profileComplete) {
//
//                    val name = binding.name.toString().trim()
//                    val school = binding.email.toString().trim()
//
//
//                    if (editMode) {
//                        val friendData = Friend(bitmapString, name, email).apply {
//                            id = idNnote
//                        }
//                        Executors.newSingleThreadExecutor().execute {
//                            friendDatabase.friendDao().update(friendData)
//                        }
//                        Toast.makeText(this, "$name telah berhasil diedit", Toast.LENGTH_SHORT)
//                            .show()
//                    } else {
//                        Executors.newSingleThreadExecutor().execute {
//                            friendDatabase.friendDao().insert(
//                                Friend(bitmapString, name, email)
//                            )
//                        }
//                        Toast.makeText(
//                            this,
//                            binding.name + " telah berhasil ditambahkan",
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//                    }

    private fun getPhotoFile(): File {
        val fileName = "photo.jpg"

        val mediaStorage = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "camera")

        if (!mediaStorage.exists() && !mediaStorage.mkdirs()) {

        }
        return File(mediaStorage.path + File.separator + fileName)
    }


}
