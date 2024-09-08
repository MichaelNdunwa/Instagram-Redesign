package com.devmichael.instagramredesign.fragments.follow_details

import android.os.Bundle
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

//                allUsers.forEach { if (it.uid in followersIdList) followersList.add(it) }
                followersList = allUsers.filter { it.uid in followersIdList }.toMutableList()

                // Set up the RecyclerView:
                binding.followersRecyclerView.apply {
                    adapter = FollowersAdapter(followersList, requireActivity() as AppCompatActivity)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}