package com.devmichael.instagramredesign.utils

import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseUtils() {

    fun userName(userId: String, textView: TextView) {
        val userRef = FirebaseDatabase.getInstance().reference
            .child("users").child(userId)
            .child("username")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    textView.text = snapshot.value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUtils", "Error fetching username", error.toException())
            }
        })
    }

    fun fullName(userId: String, textView: TextView) {
        val userRef = FirebaseDatabase.getInstance().reference
            .child("users").child(userId)
            .child("fullName")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    textView.text = snapshot.value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUtils", "Error fetching fullName", error.toException())
            }
        })
    }

    fun biography(userId: String, textView: TextView) {
        val userRef = FirebaseDatabase.getInstance().reference
            .child("users").child(userId)
            .child("bio")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    textView.text = snapshot.value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUtils", "Error fetching biography.", error.toException())
            }
        })
    }

    fun dateOfBirth(userId: String, textView: TextView) {
        val userRef = FirebaseDatabase.getInstance().reference
            .child("users").child(userId)
            .child("dateOfBirth")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    textView.text = snapshot.value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUtils", "Error fetching dateOfBirth.", error.toException())
            }
        })
    }

    fun email(userId: String, textView: TextView) {
        val userRef = FirebaseDatabase.getInstance().reference
            .child("users").child(userId)
            .child("email")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    textView.text = snapshot.value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUtils", "Error fetching email.", error.toException())
            }
        })
    }

    fun numberOfFollowing(userId: String, textView: TextView) {
        val followersRef = FirebaseDatabase.getInstance().reference
            .child("follow").child(userId)
            .child("following")

        followersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    textView.text = snapshot.childrenCount.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUtils", "Error fetching numberOfFollowing.", error.toException())
            }

        })
    }


    fun numberOfFollowers(userId: String, textView: TextView) {
        val followersRef = FirebaseDatabase.getInstance().reference
            .child("follow").child(userId)
            .child("followers")

        followersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    textView.text = snapshot.childrenCount.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUtils", "Error fetching numberOfFollowers.", error.toException())
            }

        })
    }

}