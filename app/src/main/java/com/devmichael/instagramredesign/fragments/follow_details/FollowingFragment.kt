package com.devmichael.instagramredesign.fragments.follow_details

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmichael.instagramredesign.R
import com.devmichael.instagramredesign.adapters.FollowingAdapter
import com.devmichael.instagramredesign.databinding.FragmentFollowersBinding
import com.devmichael.instagramredesign.databinding.FragmentFollowingBinding
import com.devmichael.instagramredesign.models.FollowingModel
import com.devmichael.instagramredesign.models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FollowingFragment(private val followingIdList: List<String>) : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private var followingList: MutableList<UserModel> = mutableListOf()
    private lateinit var adapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        listFollowing()
        return binding.root
    }

    private fun listFollowing() {
        // initializing:
        followingList = mutableListOf<UserModel>()
        adapter = FollowingAdapter(followingList, requireActivity() as AppCompatActivity)
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
                    if (it.uid in followingIdList)  {
//                        followingList.add(it)
                        if (it !in followingList) followingList.add(it)
                    }
                }

                // Set up the RecyclerView:
                binding.followingRecyclerView.apply {
//                    adapter = FollowingAdapter(followingList, requireActivity() as AppCompatActivity)
                    adapter = this@FollowingFragment.adapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        // Set up search bar:
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filterFollowingList(
                    followingList.filter {
                        it.username.contains(s.toString(), ignoreCase = true) ||
                                it.fullName.contains(s.toString(), ignoreCase = true)
                    }
                )
            }

            override fun afterTextChanged(s: Editable?) { }

        })

    }

}