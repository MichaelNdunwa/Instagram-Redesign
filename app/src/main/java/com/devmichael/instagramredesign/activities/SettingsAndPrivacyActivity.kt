package com.devmichael.instagramredesign.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.ActivitySettingsAndPrivacyBinding

class SettingsAndPrivacyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsAndPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsAndPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        enableToolbar()

    }

    private fun enableToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings and privacy"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}