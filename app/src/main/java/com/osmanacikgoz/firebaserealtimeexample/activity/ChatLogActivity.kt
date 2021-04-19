package com.osmanacikgoz.firebaserealtimeexample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.osmanacikgoz.firebaserealtimeexample.databinding.ActivityChatLogBinding
import com.osmanacikgoz.firebaserealtimeexample.viewholder.ChatFromHolder
import com.osmanacikgoz.firebaserealtimeexample.viewholder.ChatViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ChatLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_log)


        //val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user?.username

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatViewHolder())
        adapter.add(ChatFromHolder())
        adapter.add(ChatViewHolder())
        adapter.add(ChatFromHolder())
        adapter.add(ChatViewHolder())
        adapter.add(ChatFromHolder())
        adapter.add(ChatViewHolder())
        adapter.add(ChatFromHolder())
        binding.chatLogRecycler.adapter = adapter
    }
}