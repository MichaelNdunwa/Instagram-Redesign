package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.FollowersViewHolderBinding
import com.devmichael.instagramredesign.fragments.UserProfileFragment
import com.devmichael.instagramredesign.models.FollowersModel
import com.devmichael.instagramredesign.models.UserModel
import com.squareup.picasso.Picasso

class FollowersAdapter(private var followerList: List<UserModel>, private var activity: AppCompatActivity):
        RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private lateinit var binding: FollowersViewHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int):
            FollowersAdapter.FollowersViewHolder {
        val from = LayoutInflater.from(parent.context)
        binding = FollowersViewHolderBinding.inflate(from, parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersAdapter.FollowersViewHolder, position: Int) {
        val follower = followerList[position]
        holder.binding.apply {
            Picasso.get().load(follower.profileImage).placeholder(R.drawable.default_profile_image).into(profileImage)
            userName.text = follower.username

          /*  root.setOnClickListener {
                val fragment = UserProfileFragment(user = follower)
                val fragmentManager = activity.supportFragmentManager
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()
            }*/

            removeButton.setOnClickListener {
                Toast.makeText(activity, "Remove Button was clicked", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun getItemCount(): Int = followerList.size

    inner class FollowersViewHolder(val binding: FollowersViewHolderBinding):
            RecyclerView.ViewHolder(binding.root) {

            }

}