package com.example.mynotes.edit

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.*
import com.crocodic.core.helper.DateTimeHelper
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.Base.BaseViewModel
import com.example.mynotes.R
import com.example.mynotes.api.ApiService
import com.example.mynotes.data.AppDatabase
import com.example.mynotes.data.User
import com.example.mynotes.databinding.ActivityEditBinding
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.home.HomeActivity
import com.example.mynotes.home.HomeViewModel
import com.example.mynotes.home.screen.ProfilFragment
import com.example.mynotes.profil.ProfilActivity
import com.example.mynotes.profil.ProfilActivity_GeneratedInjector
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.Executors

@AndroidEntryPoint
class EditActivity : BaseActivity<ActivityEditBinding, EditViewModel>(R.layout.activity_edit) {


    private var photoFile: File? = null
    private var username: String? = null
    private var photo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //untuk getExstra menyimpan username
        photo = intent.getStringExtra("photoFile")
        username = intent.getStringExtra("username")
        binding.activity = this
        binding.photo = photo
        binding.etNameProfil.setText(username)

        initClick()
        observe()
    }

    //compres photo
    //foto saja yang ganti bisa namun username tetap di isi sama dan data akan memberi respon
    private fun validateForm() {
        val name = binding.etNameProfil.textOf()

        if (name.isEmpty()) {
            tos("Username tidak boleh kosong")
            return
        }

        //untuk foto saja
        if (photoFile == null) {
            if (name == username) {
                tos("Tidak ada yang berubah")
                return
            }
            viewModel.updateProfile(name)
        } else {
            lifecycleScope.launch {
                tos("Tunggu")
                val compressPhoto = compressFile(photoFile!!)
                if (compressPhoto != null) {
                    viewModel.updateProfileWithPhoto(name, compressPhoto)
                }
            }
        }

    }

    //compres photo
    suspend fun compressFile(filePhoto: File): File? {
        println("Compress 1")
        try {
            println("Compress 2")
            return Compressor.compress(this, filePhoto) {
                resolution(720, 720)
                quality(50)
                format(Bitmap.CompressFormat.JPEG)
                size(514)
            }
        } catch (e: Exception) {
            println("Compress 3")
            tos("Gagal kompress anda bisa mengganti foto lain")
            e.printStackTrace()
            return null
        }

    }


    private fun initClick() {
        binding.back.setOnClickListener {
            finish()
        }
        binding.btnSaveProfil.setOnClickListener {
            validateForm()
        }

        binding.EditPhoto.setOnClickListener {
            if (checkPermissionGallery()) {
                openGallery()
            } else {
                requestPermissionGallery()
            }
        }

    }


    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isRefresh.observe(this@EditActivity) {
                        when (it) {
                            0 -> {
                                loadingDialog.setResponse("Gagal renew token")
                            }
                            1 -> {
                                val nama = binding.etNameProfil.textOf()
                                viewModel.updateProfile(nama)
                            }
                            2 -> {
                                val nama = binding.etNameProfil.textOf()
                                if (photoFile != null) {
                                    viewModel.updateProfileWithPhoto(nama, photoFile!!)
                                }
                            }
                            else -> {
                                loadingDialog.setResponse("Else kondisi")
                            }
                        }
                        /*if (it == 0) {
                            loadingDialog.setResponse("Gagal renew token")
                        } else if (it == 1){
                            val nama = binding.etNameProfil.textOf()
                            viewModel.updateProfile(nama)
                        } else if (it == 2) {
                            val nama = binding.etNameProfil.textOf()
                            if (photoFile != null) {
                                viewModel.updateProfileWithPhoto(nama, photoFile!!)
                            }
                        } else {
                            loadingDialog.setResponse("Else kondisi")
                        }*/
                    }

                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Save Profile")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "Succes Update Profile")
                                loadingDialog.dismiss()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
//                /*launch {
//                    viewModel.getUser.observe(this@EditActivity) {
//                        it?.let { data ->
//                            binding?.activity = data
//                            binding?.let { viewImage ->
//                                //untuk profilenya
//                                Glide
//                                    .with(requireContext())
//                                    .load(it.photo)
//                                    .placeholder(R.drawable.picture)
//                                    .error(R.drawable.error)
//                                    .apply(RequestOptions.centerInsideTransform())
//                                    .into(viewImage.ivImage)
//                            }
//                        }
//                    }
//                }*/
            }
        }
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

    private var activityLauncherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.data?.let {
                generateFileImage(it)
            }
        }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityLauncherGallery.launch(galleryIntent)
    }

    private fun checkPermissionGallery(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionGallery() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            110
        )
    }

    private fun generateFileImage(uri: Uri) {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()

            val orientation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getOrientation2(uri)
            } else {
                getOrientation(uri)
            }

            val file = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                createImageFile()
            } else {
                //File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}" + File.separator + "BurgerBangor", getNewFileName())
                File(externalCacheDir?.absolutePath, getNewFileName())
            }

            val fos = FileOutputStream(file)
            var bitmap = image

            if (orientation != -1 && orientation != 0) {

                val matrix = Matrix()
                when (orientation) {
                    6 -> matrix.postRotate(90f)
                    3 -> matrix.postRotate(180f)
                    8 -> matrix.postRotate(270f)
                    else -> matrix.postRotate(orientation.toFloat())
                }
                bitmap =
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            binding.EditPhoto.setImageBitmap(bitmap)
            photoFile = file
        } catch (e: Exception) {
            e.printStackTrace()
            binding.root.snacked("File ini tidak dapat digunakan")
        }
    }

    @SuppressLint("Range")
    private fun getOrientation(shareUri: Uri): Int {
        val orientationColumn = arrayOf(MediaStore.Images.Media.ORIENTATION)
        val cur = contentResolver.query(
            shareUri,
            orientationColumn,
            null,
            null,
            null
        )
        var orientation = -1
        if (cur != null && cur.moveToFirst()) {
            if (cur.columnCount > 0) {
                orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]))
            }
            cur.close()
        }
        return orientation
    }

    @SuppressLint("NewApi")
    private fun getOrientation2(shareUri: Uri): Int {
        val inputStream = contentResolver.openInputStream(shareUri)
        return getOrientation3(inputStream)
    }

    @SuppressLint("NewApi")
    private fun getOrientation3(inputStream: InputStream?): Int {
        val exif: ExifInterface
        var orientation = -1
        inputStream?.let {
            try {
                exif = ExifInterface(it)
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return orientation
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = DateTimeHelper().createAtLong().toString()
        val storageDir =
            getAppSpecificAlbumStorageDir(Environment.DIRECTORY_DOCUMENTS, "Attachment")
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun getNewFileName(isPdf: Boolean = false): String {
        val timeStamp = DateTimeHelper().createAtLong().toString()
        return if (isPdf) "PDF_${timeStamp}_.pdf" else "JPEG_${timeStamp}_.jpg"
    }

    private fun getAppSpecificAlbumStorageDir(albumName: String, subAlbumName: String): File {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file = File(getExternalFilesDir(albumName), subAlbumName)
        if (!file.mkdirs()) {
            //Log.e("fssfsf", "Directory not created")
        }
        return file
    }


}
