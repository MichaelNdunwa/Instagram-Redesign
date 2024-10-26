package com.devmichael.instagramredesign.fragments.follow_details

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.adapters.FollowersAdapter
import com.devmichael.instagramredesign.databinding.FragmentFollowersBinding
import com.devmichael.instagramredesign.models.FollowersModel
import com.devmichael.instagramredesign.models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FollowersFragment(private val followersIdList: List<String>) : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private var followersList: MutableList<UserModel> = mutableListOf()
    private lateinit var adapter: FollowersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)

        listFollowers()

        return binding.root
    }

    private fun listFollowers() {
        // initializing:
        followersList = mutableListOf<UserModel>()
        adapter = FollowersAdapter(followersList, requireActivity() as AppCompatActivity)
        val allUsers: MutableList<UserModel> = mutableListOf()
        val userRef = FirebaseDatabase.getInstance().getReference().child("users")
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allUsers.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)
                    if (user != null) {
                        allUsers.add(user)
                    }
                }

                allUsers.forEach {
                    if (it.uid in followersIdList) {
                        if (it !in followersList) followersList.add(it)
                    }
                }

                // Set up the RecyclerView:
                binding.followersRecyclerView.apply {
//                    adapter = FollowersAdapter(followersList, requireActivity() as AppCompatActivity)
                    adapter = this@FollowersFragment.adapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        // Set up the search bar:
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int ) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int ) {
                adapter.filterFollowersList(
                    followersList.filter {
                        it.username.contains(s.toString(), ignoreCase = true) ||
                                it.fullName.contains(s.toString(), ignoreCase = true)
                    }
                )
            }

            override fun afterTextChanged(s: Editable?) { }

        })

    }

}