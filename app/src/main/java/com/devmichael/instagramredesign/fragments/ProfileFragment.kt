package com.devmichael.instagramredesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.adapters.HighlightAdapter
import com.devmichael.instagramredesign.adapters.ViewPagerAdapter
import com.devmichael.instagramredesign.databinding.FragmentProfileBinding
import com.devmichael.instagramredesign.databinding.ProfileToolbarBinding
import com.devmichael.instagramredesign.fragments.main_user_profile.ProfileSettingsDialogFragment
import com.devmichael.instagramredesign.models.HighlightModel
import com.devmichael.instagramredesign.utils.FirebaseUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var loggedInUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var firebaseUtils: FirebaseUtils = FirebaseUtils()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // get the number of followers and number of following:
        /*  numberOfFollowers()
          numberOfFollowing()*/
        firebaseUtils.numberOfFollowers(loggedInUser!!.uid, binding.followersCounter)
        firebaseUtils.numberOfFollowing(loggedInUser!!.uid, binding.followingCounter)


        firebaseUtils.userName(loggedInUser!!.uid, binding.userName)
        firebaseUtils.fullName(loggedInUser!!.uid, binding.fullName)
        firebaseUtils.biography(loggedInUser!!.uid, binding.biography)

        setUpProfileToolbar()
        createHighlight()
        tabLayoutAndViewPager()
        settingsDialog()


        return binding.root
    }
    /*
        private fun numberOfFollowers() {
            val followersRef = loggedInUser!!.uid.let { currentUserId ->
                FirebaseDatabase.getInstance().reference
                    .child("follow").child(currentUserId)
                    .child("followers")
            }

            followersRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.followersCounter.text = snapshot.childrenCount.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        private fun numberOfFollowing() {
            val followersRef = loggedInUser!!.uid.let { currentUserId ->
                FirebaseDatabase.getInstance().reference
                    .child("follow").child(currentUserId)
                    .child("following")
            }

            followersRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.followingCounter.text = snapshot.childrenCount.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }*/

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