package com.devmichael.instagramredesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.adapters.HighlightAdapter
import com.devmichael.instagramredesign.adapters.ViewPagerAdapter
import com.devmichael.instagramredesign.databinding.FragmentProfileBinding
import com.devmichael.instagramredesign.fragments.follow_details.FollowDetailsFragment
import com.devmichael.instagramredesign.fragments.main_user_profile.ProfileSettingsDialogFragment
import com.devmichael.instagramredesign.models.HighlightModel
import com.devmichael.instagramredesign.utils.FirebaseUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoggedInProfileFragment() : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var loggedInUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var firebaseUtils: FirebaseUtils = FirebaseUtils()
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        //get username:
        getUsername()

        // get the number of followers and number of following:
        /*  numberOfFollowers()
          numberOfFollowing()*/
        firebaseUtils.numberOfFollowers(loggedInUser!!.uid, binding.followersCounter)
        firebaseUtils.numberOfFollowing(loggedInUser!!.uid, binding.followingCounter)

        firebaseUtils.userName(loggedInUser!!.uid, binding.userName)
        firebaseUtils.fullName(loggedInUser!!.uid, binding.fullName)
        firebaseUtils.biography(loggedInUser!!.uid, binding.biography)

        // goto follow details:
        binding.apply {
            val fragmentManager = (activity as AppCompatActivity).supportFragmentManager
            followersLayout.setOnClickListener {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FollowDetailsFragment(
                        loggedInUser?.uid.toString(),username
                    ))
                    .addToBackStack("followersFragment").commit()
            }
            followingLayout.setOnClickListener {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FollowDetailsFragment(
                        loggedInUser?.uid.toString(), username
                    ))
                    .addToBackStack("followingFragment").commit()
            }
            postLayout.setOnClickListener { viewPager.setCurrentItem(0, true) }
        }


        setUpProfileToolbar()
        createHighlight()
        tabLayoutAndViewPager()
        settingsDialog()


        return binding.root
    }

    private fun getUsername() {
            val userRef = FirebaseDatabase.getInstance().reference
                .child("users").child(loggedInUser?.uid.toString())
                .child("username")

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        username = snapshot.value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseUtils", "Error fetching username", error.toException())
                }
            })
    }

    private fun setUpProfileToolbar() {
        val toolbar = binding.navAndTitleToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity).supportActionBar?.title = "wanda.s"
//        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.backward_icon)

        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressedDispatcher.onBackPressed()
        }

        binding.nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == 0) {
                // Scrolling has stopped at the top
                binding.navAndTitleToolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
            /* else if (scrollY == v.getChildAt(0).height - v.height) {
                // Scrolling has stopped at the bottom
            } */
            else {
                // Scrolling is still in progress
//                binding.navAndTitleToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                binding.navAndTitleToolbar.setBackgroundResource(R.drawable.toolbar_background)
            }
        }

    }

    private fun tabLayoutAndViewPager() {
        val tabIcons = arrayOf(
            R.drawable.grid_icon,
            R.drawable.video_file_icon,
            R.drawable.tag_icon,
            R.drawable.love_icon
        )
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setIcon(tabIcons[position])
        }.attach()
    }


    private fun createHighlight() {
        val highlightList = mutableListOf<HighlightModel>()
        highlightList.add(HighlightModel(R.drawable.reading_alone, "Ayo"))
        highlightList.add(HighlightModel(R.drawable.reading_alone, "Alex"))
        highlightList.add(HighlightModel(R.drawable.reading_alone, "Toa"))
        highlightList.add(HighlightModel(R.drawable.reading_alone, "Christina"))
        highlightList.add(HighlightModel(R.drawable.reading_alone, "Joseph"))
        highlightList.add(HighlightModel(R.drawable.reading_alone, "Charles"))
        highlightList.add(HighlightModel(R.drawable.reading_alone, "Michael"))

        binding.highlightRecyclerView.apply {
            adapter = HighlightAdapter(highlightList)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun settingsDialog() {
        binding.menuButton.setOnClickListener {
            val bottomSheetDialog = ProfileSettingsDialogFragment()
            bottomSheetDialog.show(this.childFragmentManager, "ProfileSettingsDialogFragment")
        }
    }
}