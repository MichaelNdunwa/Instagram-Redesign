package com.devmichael.instagramredesign.signup_and_login

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.activities.MyFeedActivity
import com.devmichael.instagramredesign.databinding.ActivityLandingBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.firebase.auth.FirebaseAuth

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.userSignInButton.setOnClickListener {
           startActivity(Intent(this, UserSignInActivity::class.java))
        }

        binding.facebookLogIn.setOnClickListener {
            startActivity(Intent(this, FacebookLogInActivity::class.java))
        }

        binding.userSignUp.setOnClickListener {
            startActivity(Intent(this, UserSignUpActivity::class.java))
        }

/*        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                hideKeyboard(currentFocus ?: binding.root)
            }
            false
        }*/

    }


    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, MyFeedActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}