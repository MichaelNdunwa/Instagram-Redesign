package com.devmichael.instagramredesign.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.FollowingViewHolderBinding
import com.devmichael.instagramredesign.dialogs.FollowMenuDialogFragment
import com.devmichael.instagramredesign.fragments.LoggedInProfileFragment
import com.devmichael.instagramredesign.fragments.UserProfileFragment
import com.devmichael.instagramredesign.models.FollowingModel
import com.devmichael.instagramredesign.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class FollowingAdapter(
    private var followingList: List<UserModel>,
    private var activity: AppCompatActivity
) :
    RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private lateinit var binding: FollowingViewHolderBinding
    private var loggedInUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    fun filterFollowingList(filteredList: List<UserModel>) {
        followingList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val from = LayoutInflater.from(parent.context)
        binding = FollowingViewHolderBinding.inflate(from, parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val following = followingList[position]
        holder.binding.apply {
            Picasso.get().load(following.profileImage).placeholder(R.drawable.default_profile_image).into(profileImage)
            userName.text = following.username

            // When the profile image is clicked:
            profileImage.setOnClickListener {
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UserProfileFragment(following)).addToBackStack(null).commit()
            }

            // Set onClickListener for the entire view:
            root.setOnClickListener {
                activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UserProfileFragment(following)).addToBackStack(null).commit()
            }

           followingButton.setOnClickListener {
//               followButtonAction()
               val followingRef = loggedInUser?.uid.let {
                   FirebaseDatabase.getInstance().reference
                       .child("follow").child(it.toString())
                       .child("following")
               }
               followingRef.addValueEventListener(object: ValueEventListener {
                   override fun onDataChange(snapshot: DataSnapshot) {
                       if (snapshot.child(following.uid).exists()) {
                           followingButton.text = "Following"
                           followingButton.setTextColor(Color.BLACK)
                       } else {
                           followingButton.apply {
                               text = "Follow"
                               setTextColor(Color.WHITE)
                           }
                       }
                   }

                   override fun onCancelled(error: DatabaseError) {
                       TODO("Not yet implemented")
                   }
               })

               // Unfollow action:
               if (followingButton.text == "Following") {
                   loggedInUser?.uid.let { loggedInUserId ->
                       FirebaseDatabase.getInstance().reference
                           .child("follow").child(loggedInUserId.toString())
                           .child("following").child(following.uid)
                           .removeValue().addOnCompleteListener { task ->
                               if (task.isSuccessful) {
                                   FirebaseDatabase.getInstance().reference
                                       .child("follow").child(following.uid)
                                       .child("followers").child(loggedInUserId.toString())
                                       .removeValue()
                               }
                           }
                   }
               } else {
                   loggedInUser?.uid.let { loggedInUserId ->
                       FirebaseDatabase.getInstance().reference
                           .child("follow").child(loggedInUserId.toString())
                           .child("following").child(following.uid)
                           .setValue(true).addOnCompleteListener { task ->
                               if (task.isSuccessful) {
                                   FirebaseDatabase.getInstance().reference
                                       .child("follow").child(following.uid)
                                       .child("followers").child(loggedInUserId.toString())
                                       .setValue(true)
                               }
                           }

                   }
               }

           }

            moreOptions.visibility = if (following.uid == loggedInUser?.uid)  View.GONE else View.VISIBLE
            moreOptions.setOnClickListener {
                FollowMenuDialogFragment(following).show(activity.supportFragmentManager, "FollowMenuDialogFragment")
            }

        }
    }

    private fun followButtonAction() {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = followingList.size

    inner class FollowingViewHolder(val binding: FollowingViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}
