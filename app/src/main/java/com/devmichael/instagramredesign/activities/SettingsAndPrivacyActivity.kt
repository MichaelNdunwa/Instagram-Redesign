package com.devmichael.instagramredesign.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.ActivitySettingsAndPrivacyBinding
import com.devmichael.instagramredesign.signup_and_login.UserSignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.values

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

        binding.logoutCurrentUser.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, UserSignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }

    private fun enableToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings and privacy"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

/*    private fun getUserName(): String {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(uid!!)
        *//*val username = databaseReference.child("username").get().addOnSuccessListener { snapshot ->
            snapshot.getValue(String::class.java)
        }*//*
        val username = databaseReference.child("username").get()

        return username.toString()
    }*/
}