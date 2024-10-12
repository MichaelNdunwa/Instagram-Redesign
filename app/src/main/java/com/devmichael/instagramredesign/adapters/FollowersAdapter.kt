package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.FollowersViewHolderBinding
import com.devmichael.instagramredesign.dialogs.RemoveDialogFragment
import com.devmichael.instagramredesign.fragments.UserProfileFragment
import com.devmichael.instagramredesign.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso

class FollowersAdapter(private var followerList: List<UserModel>, private var activity: AppCompatActivity):
        RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private lateinit var binding: FollowersViewHolderBinding
    private var loggedInUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

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

            // When the profile image is clicked:
            profileImage.setOnClickListener {
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UserProfileFragment(follower)).addToBackStack(null).commit()
            }

            // Set onClickListener for the entire view:
            root.setOnClickListener {
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UserProfileFragment(follower)).addToBackStack(null).commit()
            }

            removeButton.visibility = if (follower.uid == loggedInUser?.uid) View.GONE else View.VISIBLE
            removeButton.setOnClickListener {
//                Toast.makeText(activity, "Remove Button was clicked", Toast.LENGTH_SHORT).show()
                RemoveDialogFragment(follower, activity).show(activity.supportFragmentManager, "RemoveDialogFragment")
            }
        }


    }

    override fun getItemCount(): Int = followerList.size

    inner class FollowersViewHolder(val binding: FollowersViewHolderBinding):
            RecyclerView.ViewHolder(binding.root) {

            }

}