package com.devmichael.instagramredesign.signup_and_login

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.activities.MyFeedActivity
import com.devmichael.instagramredesign.databinding.ActivityUserSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignUpBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.navBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.main.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
               val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
               imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
            false
        }

        /*binding.usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.usernameEditText.apply {
                    setText(s.toString().lowercase())
//                    binding.usernameEditText.text?.let { setSelection(it.length) }
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                binding.usernameEditText.apply {
                    setText(s.toString().lowercase())
                    binding.usernameEditText.text?.let { setSelection(it.length) }
                }
            }

        })*/

//        changeButtonBackground()
        binding.createAccountButton.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {
        val username = binding.usernameEditText.text.toString().lowercase()
        val firstName = binding.firstnameEditText.text.toString()
        val lastName = binding.lastnameEditText.text.toString()
        val fullName = "$firstName $lastName"
        val dateOfBirth = binding.dateBirthEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()
        val acceptedPassword = if (password == confirmPassword) password else null

        when {
            TextUtils.isEmpty(username) -> Toast.makeText( this, "Username is required", Toast.LENGTH_SHORT ).show()

            TextUtils.isEmpty(firstName) -> Toast.makeText( this, "First name is required", Toast.LENGTH_SHORT ).show()

            TextUtils.isEmpty(lastName) -> Toast.makeText( this, "Last name is required", Toast.LENGTH_SHORT ).show()

            TextUtils.isEmpty(dateOfBirth) -> Toast.makeText( this, "Date of birth is required", Toast.LENGTH_SHORT ).show()

            TextUtils.isEmpty(email) -> Toast.makeText( this, "Email is required", Toast.LENGTH_SHORT ).show()

            TextUtils.isEmpty(acceptedPassword) -> Toast.makeText( this, "Incorrect or empty password", Toast.LENGTH_SHORT ).show()

            else -> {
                //TODO: Create account
                val progressDialog = ProgressDialog(this).apply {
                    setTitle("Creating account...")
                    setMessage("Setting up your account, this will take a sec...")
                    setCanceledOnTouchOutside(false)
                    show()
                }
                val mAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email, acceptedPassword.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUserInfo(username, fullName, email, dateOfBirth, progressDialog)
                        } else {
                            val errorMessage = task.exception?.message
                            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }


//                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveUserInfo(
        username: String,
//        firstName: String,
//        lastName: String,
        fullName: String,
        email: String,
        dateOfBirth: String,
        progressDialog: ProgressDialog
    ) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
//        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(currentUserId)
        val userRef = FirebaseDatabase.getInstance().reference.child("users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["username"] = username.lowercase()
//        userMap["firstName"] = firstName
//        userMap["lastName"] = lastName
        userMap["fullName"] = fullName
        userMap["email"] = email
        userMap["dateOfBirth"] = dateOfBirth
        userMap["bio"] = "Hey, I'm using Instagram!"
        userMap["profileImage"] = "https://firebasestorage.googleapis.com/v0/b/instagram-redesign-389e6.appspot.com/o/DefaultImages%2FteddyBear.jpeg?alt=media&token=818ad98b-2e8f-45e0-8f9e-e8a99d15cf8f"

        userRef.child(currentUserId).setValue(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressDialog.dismiss()
                val intent = Intent(this, MyFeedActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val errorMessage = task.exception?.message
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
        }

    }

    private fun getDefaultProfileImage(): StorageReference {
        val storageRef = FirebaseStorage.getInstance().reference
        val image = storageRef.child("DefaultImages/teddyBear.jpeg")
        return image
    }

    private fun changeButtonBackground() {

        val username = binding.usernameEditText.text.toString()
        val firstName = binding.firstnameEditText.text.toString()
        val lastName = binding.lastnameEditText.text.toString()
        val dateOfBirth = binding.dateBirthEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        when {
            TextUtils.isEmpty(username) && TextUtils.isEmpty(firstName) && TextUtils.isEmpty(
                lastName
            )
                    && TextUtils.isEmpty(dateOfBirth) && TextUtils.isEmpty(email) && TextUtils.isEmpty(
                password
            )
                    && TextUtils.isEmpty(confirmPassword) -> {
//                        TODO: Not yet implemented
            }

            else -> {
                binding.createAccountButton.apply {
                    setBackgroundResource(R.drawable.active_button_background)
                    setTextColor(ContextCompat.getColor(this@UserSignUpActivity, R.color.black))
                }
            }

        }
        val isAnyFieldEmpty = listOf(
            binding.usernameEditText.text.toString(), binding.firstnameEditText.text.toString(),
            binding.lastnameEditText.text.toString(),
            binding.dateBirthEditText.text.toString(),
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString(),
            binding.confirmPasswordEditText.text.toString()
        ).any { it.isEmpty() }

        if (isAnyFieldEmpty) {
            // TODO: Handle the case when any field is empty, e.g., show an error message
        } else {
            binding.createAccountButton.apply {
                setBackgroundResource(R.drawable.active_button_background)
                setTextColor(ContextCompat.getColor(this@UserSignUpActivity, R.color.black))
            }
        }

    }

}