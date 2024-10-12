package com.devmichael.instagramredesign.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devmichael.instagramredesign.fragments.follow_details.FollowersFragment
import com.devmichael.instagramredesign.fragments.follow_details.FollowingFragment
import com.devmichael.instagramredesign.fragments.follow_details.SubscriptionFragment
import com.devmichael.instagramredesign.models.FollowersModel
import com.devmichael.instagramredesign.models.FollowingModel

class FollowDetailsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val followersList: List<String>,
    private val followingList: List<String>
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment(followersList)
            1 -> FollowingFragment(followingList)
            2 -> SubscriptionFragment()
            else -> FollowersFragment(followersList)
        }
    }

}