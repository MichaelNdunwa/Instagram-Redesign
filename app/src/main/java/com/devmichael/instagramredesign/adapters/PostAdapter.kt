package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.PostViewHolderBinding
import com.devmichael.instagramredesign.models.PostModel

class PostAdapter(private val postList: MutableList<PostModel>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private lateinit var binding: PostViewHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        val from = LayoutInflater.from(parent.context)
        binding = PostViewHolderBinding.inflate(from, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        val post = postList[position]
        holder.binding.name.text = post.profileName
        holder.binding.postProfileImage.setImageResource(post.picture)
        holder.binding.location.text = post.location
        holder.binding.data.text = post.date
        holder.binding.postImage.setImageResource(post.post)
        holder.binding.captionName.text = post.profileName
        holder.binding.captionText.text = post.caption
    }

    override fun getItemCount(): Int {
       return postList.size
    }

    inner class PostViewHolder(val binding: PostViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.likeButton.setOnClickListener {
                val post = postList[adapterPosition]

                if (post.liked) binding.likeButton.setImageResource(R.drawable.not_like_icon)
                else binding.likeButton.setImageResource(R.drawable.liked_icon)
                post.liked = !post.liked
            }

            binding.bookmarkButton.setOnClickListener {
                val post = postList[adapterPosition]
                if (post.bookmarked) {
                    binding.bookmarkButton.setImageResource(R.drawable.un_bookmark_icon)
                    post.bookmarked = !post.bookmarked
                } else {
                    binding.bookmarkButton.setImageResource(R.drawable.bookmark_icon)
                    post.bookmarked = !post.bookmarked
                }
            }

        }

    }

}