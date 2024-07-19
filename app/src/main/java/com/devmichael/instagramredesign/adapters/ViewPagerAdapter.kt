package com.devmichael.instagramredesign.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devmichael.instagramredesign.fragments.main_user_profile.LikeFragment
import com.devmichael.instagramredesign.fragments.main_user_profile.MyPostFragment
import com.devmichael.instagramredesign.fragments.main_user_profile.MyVidoeFragment
import com.devmichael.instagramredesign.fragments.main_user_profile.TagFragment


class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
//        return fragments[position]
        return when (position) {
            0 -> MyPostFragment()
            1 -> MyVidoeFragment()
            2 -> TagFragment()
            3 -> LikeFragment()
            else -> MyPostFragment()
        }
    }

}
