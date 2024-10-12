package com.devmichael.instagramredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.databinding.SearchViewResultHolderBinding
import com.devmichael.instagramredesign.fragments.LoggedInProfileFragment
import com.devmichael.instagramredesign.fragments.UserProfileFragment
import com.devmichael.instagramredesign.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class SearchAdapter(private var searchList: List<UserModel>, private var activity: AppCompatActivity) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: SearchViewResultHolderBinding
    private var filteredSearchList = listOf<UserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val from = LayoutInflater.from(parent.context)
        binding = SearchViewResultHolderBinding.inflate(from, parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount() = filteredSearchList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchResult = filteredSearchList[position]
        holder.binding.apply {
            Picasso.get().load(searchResult.profileImage).placeholder(R.drawable.default_profile_image).into(profileImage)
            userName.text = searchResult.username
            fullName.text = searchResult.fullName

            root.setOnClickListener {
                val loggedInUserId = FirebaseAuth.getInstance().currentUser!!.uid
                if (loggedInUserId == searchResult.uid) {
                    activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, LoggedInProfileFragment()).addToBackStack(null).commit()
                 } else {
                    val fragment = UserProfileFragment(user = searchResult)
                    val fragmentManager = activity.supportFragmentManager
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()
                }

            }
        }
    }

    fun filter(text: String) {
        val query = text.trim()
        filteredSearchList = if (query.isEmpty()) {
            listOf<UserModel>()
        } else {
            searchList.filter { search ->
                search.username.contains(query, ignoreCase = true) || search.fullName.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(val binding: SearchViewResultHolderBinding) :
        RecyclerView.ViewHolder(binding.root)

}