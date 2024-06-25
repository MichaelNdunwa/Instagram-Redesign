package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.databinding.MyPostItemViewHolderBinding
import com.devmichael.instagramredesign.models.MyPostModel

class MyPostAdapter(private val myPostList: MutableList<MyPostModel>) :
    RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder>() {

    private lateinit var binding: MyPostItemViewHolderBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPostAdapter.MyPostViewHolder {
       val from = LayoutInflater.from(parent.context)
       binding = MyPostItemViewHolderBinding.inflate(from, parent, false)
       return MyPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        val post = myPostList[position]
        holder.binding.postImage.setImageResource(post.image)
    }

    override fun getItemCount(): Int {
        return myPostList.size
    }

    inner class MyPostViewHolder(val binding: MyPostItemViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}