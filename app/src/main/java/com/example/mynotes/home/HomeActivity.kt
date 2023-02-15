package com.example.mynotes.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.extension.openActivity
import com.example.mynotes.Base.BaseActivity
import com.example.mynotes.R
import com.example.mynotes.add.AddNoteActivity
import com.example.mynotes.databinding.ActivityHomeBinding
import com.example.mynotes.home.screen.HomeFragment
import com.example.mynotes.home.screen.ProfilFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    private val homeFragment = HomeFragment()
    private val profilFragment = ProfilFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigationView.setBackground(null)

        binding.btnAddHome.setOnClickListener {
            openActivity<AddNoteActivity> {
            }
        }
        replesFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeMenu -> {

                    replesFragment(homeFragment)
                }
                R.id.profileMenu -> {

                    replesFragment(profilFragment)
                }
            }
            true
        }
    }

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








