package com.devmichael.instagramredesign.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.ActivityFeedMyBinding
import com.devmichael.instagramredesign.fragments.ExploreFragment
import com.devmichael.instagramredesign.fragments.HomeFragment
import com.devmichael.instagramredesign.fragments.NotificationsFragment
import com.devmichael.instagramredesign.fragments.LoggedInProfileFragment

class MyFeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFeedMyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNavigation()

    }

    private fun bottomNavigation() {
        val profileImage = imageDrawableToIcon(R.drawable.alex_profile_image)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, HomeFragment()).commit()
//        binding.bottomNavigationView.menu.getItem(4).setIcon(R.drawable.alex_profile_image)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home -> openFragment(HomeFragment())
                R.id.explore -> openFragment(ExploreFragment())
                R.id.add_post -> {
                    val intent = Intent(this, AddPostActivity::class.java)
                    startActivity(intent)
                }
                R.id.notifications -> openFragment(NotificationsFragment())
                R.id.profile -> openFragment(LoggedInProfileFragment())
            }
            true
        }
    }

    // Convert Image Drawable to Icon:
    private fun imageDrawableToIcon(imageDrawable: Int): Drawable {
        return ContextCompat.getDrawable(this, imageDrawable)!!
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null).commit()
    }

}