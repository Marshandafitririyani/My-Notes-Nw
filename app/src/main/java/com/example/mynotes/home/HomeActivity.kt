package com.example.mynotes.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.add.AddNoteActivity
import com.example.mynotes.data.Note
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.databinding.CustomeCardBinding
import com.example.mynotes.home.screen.HomeFragment
import com.example.mynotes.home.screen.ProfilFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    private val homeFragment = HomeFragment()
    private val profilFragment = ProfilFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //untuk melengkungkan
        binding.bottomNavigationView.setBackground(null)

        //untuk flotingbottomnya
        binding.btnAddHome.setOnClickListener {
            openActivity<AddNoteActivity> {

            }
        }
        replesFragment(homeFragment)

        //untuk frgmentnya
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeMenu -> {
//                    tos("Home")
                    replesFragment(homeFragment)
                }
                R.id.profileMenu -> {
//                    tos("Profile")
                    replesFragment(profilFragment)
                }
            }
            true
        }

        /*lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Loggin in")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<AddNoteActivity>()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }*/
    }

/*        buttonMasuk = findViewById(R.id.btnAddHome)

        buttonMasuk.setOnClickListener {
            val masuk = Intent(this, AddNoteActivity::class.java)
            startActivity(masuk)
        }*/


    //untuk fragmentnya
    private fun replesFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framcontainer, fragment)
            commit()
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isRefresh.observe(this@HomeActivity) {
                        when (it) {
                            0 -> {
                                loadingDialog.setResponse("Gagal renew token")
                            }
                            1 -> {
                                viewModel.getNote()
                            }
                            else -> {
                                loadingDialog.setResponse("Else kondisi")
                            }
                        }
                    }
                }
            }
        }
    }
}








