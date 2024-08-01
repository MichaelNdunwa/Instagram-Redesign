package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.FollowingViewHolderBinding
import com.devmichael.instagramredesign.models.FollowingModel
import com.devmichael.instagramredesign.models.UserModel
import com.squareup.picasso.Picasso

class FollowingAdapter(
    private var followingList: List<UserModel>,
    private var activity: AppCompatActivity
) :
    RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private lateinit var binding: FollowingViewHolderBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowingAdapter.FollowingViewHolder {
        val from = LayoutInflater.from(parent.context)
        binding = FollowingViewHolderBinding.inflate(from, parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.FollowingViewHolder, position: Int) {
        val following = followingList[position]
        holder.binding.apply {
            Picasso.get().load(following.profileImage).placeholder(R.drawable.default_profile_image).into(profileImage)
            userName.text = following.username


           followingButton.setOnClickListener {
               Toast.makeText(activity, "You just clicked on the following button", Toast.LENGTH_SHORT).show()
           }
        }
    }

    override fun getItemCount(): Int = followingList.size

    inner class FollowingViewHolder(val binding: FollowingViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}
