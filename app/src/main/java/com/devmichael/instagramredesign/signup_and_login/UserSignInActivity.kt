package com.devmichael.instagramredesign.signup_and_login

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.activities.MyFeedActivity
import com.devmichael.instagramredesign.databinding.ActivityUserSignInBinding
import com.google.firebase.auth.FirebaseAuth

class UserSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignInBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.facebookLogIn.setOnClickListener {
            startActivity(Intent(this, FacebookLogInActivity::class.java))
        }

        binding.userSignUpButton.setOnClickListener {
            startActivity(Intent(this, UserSignUpActivity::class.java))
        }

        binding.logInButton.setOnClickListener {
            logInUser()
        }

        // Hide keyboard when user taps outside of it
        binding.main.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
            false
        }

        /*binding.main.setOnClickListener {
            hideKeyboard(binding.main)
        }*/
    }

    override fun onStart() {
        super.onStart()

        /* if (FirebaseAuth.getInstance().currentUser != null) {
             val intent = Intent(this, MyFeedActivity::class.java)
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
             startActivity(intent)
             finish()
         }*/
    }

    private fun logInUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        when {
            TextUtils.isEmpty(email) -> Toast.makeText(
                this,
                "email is required.",
                Toast.LENGTH_SHORT
            ).show()

            TextUtils.isEmpty(password) -> Toast.makeText(
                this,
                "password is required.",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                /*val progressDialog = ProgressDialog(this).apply {
                    setTitle("Login...")
                    setMessage("Please wait, this will take a sec...")
                    setCanceledOnTouchOutside(false)
                    show()
                }*/

                val dialog = Dialog(this).apply {
                    setContentView(R.layout.progress_dialog)
                    setCancelable(false)
                    findViewById<TextView>(R.id.progress_message).text =
                        "Please wait, this will take a sec.."
                    show()
                }

                val mAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        dialog.dismiss()
                        val intent = Intent(this, MyFeedActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()

                    } else {
                        val errorMessage = task.exception?.message
                        Toast.makeText(this, "error: $errorMessage", Toast.LENGTH_LONG).show()
                        mAuth.signOut()
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    /*private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }*/

}