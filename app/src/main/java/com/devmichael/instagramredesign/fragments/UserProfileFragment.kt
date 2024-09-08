package com.devmichael.instagramredesign.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.adapters.HighlightAdapter
import com.devmichael.instagramredesign.adapters.ViewPagerAdapter
import com.devmichael.instagramredesign.databinding.FragmentOtherUserProfileBinding
import com.devmichael.instagramredesign.dialogs.UserManagementDialogFragment
import com.devmichael.instagramredesign.fragments.follow_details.FollowDetailsFragment
import com.devmichael.instagramredesign.models.HighlightModel
import com.devmichael.instagramredesign.models.UserModel
import com.devmichael.instagramredesign.utils.FirebaseUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class UserProfileFragment(private val user: UserModel) : Fragment() {

    private var _binding: FragmentOtherUserProfileBinding? = null
    val binding get() = _binding!!
    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var notFollowing: Boolean? = null
    private var firebaseUtils: FirebaseUtils = FirebaseUtils()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtherUserProfileBinding.inflate(inflater, container, false)

        // Load profile details
        binding.apply {
            fullName.text = user.fullName
            Picasso.get().load(user.profileImage).placeholder(R.drawable.default_profile_image)
                .into(profileImage)
            biography.text = user.bio

            // get number of followers and followings:
            firebaseUtils.numberOfFollowers(user.uid, binding.followersCounter)
            firebaseUtils.numberOfFollowing(user.uid, binding.followingCounter)

            // goto follow details:
            val fragmentManager = (activity as AppCompatActivity).supportFragmentManager
            followersLayout.setOnClickListener {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FollowDetailsFragment(user.uid))
                    .addToBackStack("followersFragment").commit()
            }
            followingLayout.setOnClickListener {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FollowDetailsFragment(user.uid))
                    .addToBackStack("followingFragment").commit()
            }
            postLayout.setOnClickListener { viewPager.setCurrentItem(0, true) }
        }

//        followAction()
        setUpProfileToolbar()
        createHighlight()
        tabLayoutAndViewPager()
        manageUserMenu()

        menuVisibility()
        binding.followButton.setOnClickListener { followAction() }
        checkFollowingStatus(user.uid, binding.followButton)
//        menuVisibility()

        //Get the total number of followers and following:
        firebaseUtils.numberOfFollowers(user.uid, binding.followersCounter)
        firebaseUtils.numberOfFollowing(user.uid, binding.followingCounter)
//        binding.followersCounter.text = firebaseUtils.numberOfFollowers(user.uid)
//        binding.followingCounter.text = firebaseUtils.numberOfFollowing(user.uid)


        return binding.root
    }


    private fun setUpProfileToolbar() {
        val toolbar = binding.navAndTitleToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = user.username

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
                //binding.navAndTitleToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
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
        highlightList.add(HighlightModel(R.drawable.status_image_1, "Ayo"))
        highlightList.add(HighlightModel(R.drawable.status_image_2, "Alex"))
        highlightList.add(HighlightModel(R.drawable.status_image_3, "Toa"))
        highlightList.add(HighlightModel(R.drawable.status_image_4, "Christina"))
        highlightList.add(HighlightModel(R.drawable.status_image_5, "Joseph"))
        highlightList.add(HighlightModel(R.drawable.status_image_6, "Charles"))
        highlightList.add(HighlightModel(R.drawable.status_image_7, "Michael"))

        binding.highlightRecyclerView.apply {
            adapter = HighlightAdapter(highlightList)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun menuVisibility() {
        binding.followButton.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.followButton.text.toString() == "Follow") {
                    binding.menuButton.visibility = View.GONE
                    notFollowing = true
                } else {
                    binding.menuButton.visibility = View.VISIBLE
                    notFollowing = false
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    fun checkFollowingStatus(uid: String, followButton: TextView) {
        val followingRef = firebaseUser?.uid.let { it ->
            FirebaseDatabase.getInstance().reference
                .child("follow").child(it.toString())
                .child("following")
        }
        followingRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(uid).exists()) {
                    followButton.text = "Following"
                } else {
                    followButton.text = "Follow"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun manageUserMenu() {
        binding.menuButton.setOnClickListener {
            val bottomSheetDialog =
                UserManagementDialogFragment(
                    userName = user.username,
                    notFollowing = notFollowing,
                    firebaseUser = firebaseUser,
                    user = user
                )
            bottomSheetDialog.show(this.childFragmentManager, "UserManagementFragment")
        }
    }

    private fun followAction() {
        if (notFollowing!!) {
            firebaseUser?.uid.let { currentUserId ->
                FirebaseDatabase.getInstance().reference
                    .child("follow").child(currentUserId.toString())
                    .child("following").child(user.uid)
                    .setValue(true).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                                    firebaseUser?.uid.let { currentUserId->
                            FirebaseDatabase.getInstance().reference
                                .child("follow").child(user.uid)
                                .child("followers").child(currentUserId.toString())
                                .setValue(true).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                    }
                                }
//                                    }
                        }
                    }
            }
        }
    }
}
