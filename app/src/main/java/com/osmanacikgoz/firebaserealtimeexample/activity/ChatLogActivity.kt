package com.osmanacikgoz.firebaserealtimeexample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.ChatMessage
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.osmanacikgoz.firebaserealtimeexample.databinding.ActivityChatLogBinding
import com.osmanacikgoz.firebaserealtimeexample.viewholder.ChatFromHolder
import com.osmanacikgoz.firebaserealtimeexample.viewholder.ChatViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ChatLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatLogBinding

    val adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_log)

        binding.chatLogRecycler.adapter = adapter

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user?.username
        // setupDummyData()
        listenForMessage()
        binding.chatSendButton.setOnClickListener {
            performSendMessage()
        }

    }

    private fun listenForMessage() {
        val ref = FirebaseDatabase.getInstance().getReference("/message")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMeesage = snapshot.getValue(ChatMessage::class.java)

                if (chatMeesage != null) {

                    if (chatMeesage.formId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatViewHolder(chatMeesage.text))
                    } else {


                        adapter.add(ChatFromHolder(chatMeesage.text))
                    }
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    private fun performSendMessage() {

        val text = binding.chatLogMessage.text.toString()
        val formId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user?.uid

        if (formId == null) return
        val reference = FirebaseDatabase.getInstance().getReference("/message").push()
        val chatMessage =
            ChatMessage(reference.key!!, text, formId, toId!!, System.currentTimeMillis() / 1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {

            }
    }

    private fun setupDummyData() {

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatViewHolder("selam"))
        adapter.add(ChatFromHolder("selam"))
        adapter.add(ChatViewHolder("nasılsın"))
        adapter.add(ChatFromHolder("iyi sen"))
        adapter.add(ChatViewHolder("iyi ya yuvarlanıyoruz"))
        adapter.add(ChatFromHolder("sende durumlar nasıl"))
        adapter.add(ChatViewHolder("aynı işte ne olsun"))
        adapter.add(ChatFromHolder("hayırlı işler"))

    }
}