package com.devmichael.instagramredesign.fragments.follow_details

import android.os.Bundle
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
    private val followingList: MutableList<UserModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        listFollowing()
        return binding.root
    }

    private fun listFollowing() {
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
                    if (it.uid in followingIdList) followingList.add(it)
                }

          /*     Toast.makeText(activity, "Following List: $followingList", Toast.LENGTH_SHORT).show()
                Log.d("FollowingList", "Following List: $followingList")*/
                // Set up the RecyclerView:
                binding.followingRecyclerView.apply {
                    adapter = FollowingAdapter(followingList, requireActivity() as AppCompatActivity)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

}