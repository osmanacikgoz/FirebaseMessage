package com.osmanacikgoz.firebaserealtimeexample.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.osmanacikgoz.firebaserealtimeexample.databinding.ActivityNewMessageBinding
import com.osmanacikgoz.firebaserealtimeexample.viewholder.UserViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class NewMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_message)

        //fetchUsers()
        supportActionBar?.title = "Select User"
        fetchUser()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUser() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    Log.d("New Message", it.toString())

                    val user: User? = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserViewHolder(user))
                    }
                }

                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserViewHolder
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    //intent.putExtra(USER_KEY, userItem.user.username)
                    intent.putExtra(USER_KEY,userItem.user)
                    startActivity(intent)
                    finish()
                }
                binding.recyclerNewmessage.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}