package com.devmichael.instagramredesign.fragments.follow_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devmichael.instagramredesign.adapters.FollowDetailsAdapter
import com.devmichael.instagramredesign.databinding.FragmentFolowDetailsBinding
import com.devmichael.instagramredesign.models.FollowersModel
import com.devmichael.instagramredesign.models.FollowingModel
import com.devmichael.instagramredesign.utils.FirebaseUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FollowDetailsFragment(val userId: String) : Fragment() {

    private var _binding: FragmentFolowDetailsBinding? = null
    private val binding get() = _binding!!
    private var firebaseUtils: FirebaseUtils = FirebaseUtils()
    private val loggedInUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var numberOfFollowers: Int? = null
    private var numberOfFollowing: Int? = null
    private var followersList: MutableList<FollowersModel> = mutableListOf()
    private var followingList: MutableList<FollowingModel> = mutableListOf()
    private var followersIdList: MutableList<String> = mutableListOf()
    private var followingIdList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFolowDetailsBinding.inflate(inflater, container, false)

        getNumberOfFollowersAndFollowing()
        setUpToolbar()
        return binding.root
    }


    private fun getNumberOfFollowersAndFollowing() {
//        val userId = loggedInUser!!.uid

        val followRef = FirebaseDatabase.getInstance().reference
            .child("follow").child(userId)

        followRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val followersSnapshot = snapshot.child("followers")
                val followingSnapshot = snapshot.child("following")
                numberOfFollowers = followersSnapshot.childrenCount.toInt()
                numberOfFollowing = followingSnapshot.childrenCount.toInt()

                followersList.clear()
                followersSnapshot.children.forEach {
                    followersIdList.add(it.key.toString())
                }

                followingIdList.clear()
                followingSnapshot.children.forEach {
                    followingIdList.add(it.key.toString())
                }

//                FollowersFragment(followersList) // passing the list of followers to the FollowersFragment
//                FollowingFragment(followingList) // passing the list of following to the FollowingFragment
               /* Toast.makeText(activity, "Following List: $followersIdList", Toast.LENGTH_LONG).show()
                Log.d("FollowingIdList", "Following List: $followersIdList")*/
                setUpSwipeView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun setUpToolbar() {
        val toolbar = binding.toolbar
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.title = "Follow Details"
//            supportActionBar?.setIcon(R.drawable.backward_icon)
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Set the text of the toolbar
        // The the profile username from the firebase utils I created:
        firebaseUtils.userName(loggedInUser!!.uid, binding.username)
    }

    private fun setUpSwipeView() {

        binding.tabLayout.tabMode = TabLayout.MODE_FIXED
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
//        binding.viewpager.adapter = FollowDetailsAdapter(childFragmentManager, lifecycle, followersList, followingList)
        binding.viewpager.adapter = FollowDetailsAdapter(childFragmentManager, lifecycle, followersIdList, followingIdList)
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "$numberOfFollowers followers"
                1 -> tab.text = "$numberOfFollowing following"
                2 -> tab.text = "Subscription"
            }
        }.attach()
    }

}