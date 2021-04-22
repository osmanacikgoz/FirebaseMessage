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
import com.osmanacikgoz.firebaserealtimeexample.viewholder.ChatFromHolderRight
import com.osmanacikgoz.firebaserealtimeexample.viewholder.ChatViewHolderLeft
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class ChatLogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatLogBinding

    var toUser: User? = null

    val adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_log)

        binding.chatLogRecycler.adapter = adapter

        toUser = intent.getParcelableExtra(NewMessageActivity.USER_KEY)

        supportActionBar?.title = toUser?.username
        listenForMessage()
        binding.chatSendButton.setOnClickListener {
            performSendMessage()
        }

    }

    private fun listenForMessage() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid

        val ref = FirebaseDatabase.getInstance().getReference("/user-message/$fromId/$toId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    if (chatMessage.formId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LastetMessageActivity.currentUser ?: return
                        adapter.add(ChatViewHolderLeft(chatMessage.text, currentUser))
                    } else {
                        toUser?.let {
                            adapter.add(ChatFromHolderRight(chatMessage.text, it))
                        }
                    }
                    binding.chatLogRecycler.scrollToPosition(adapter.itemCount -1)
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
        val reference = FirebaseDatabase.getInstance()
            .getReference("/user-message/$formId/$toId").push()

        val toReference = FirebaseDatabase.getInstance()
            .getReference("/user-message/$toId/$formId").push()

        val chatMessage = ChatMessage(reference.key!!, text, formId, toId!!, System.currentTimeMillis() / 1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                binding.chatLogMessage.text.clear()
                 binding.chatLogRecycler.scrollToPosition(adapter.itemCount -1 )
            }
        toReference.setValue(chatMessage)

        val lastetMessageRef = FirebaseDatabase.getInstance().getReference("/lastest-message/$formId/$toId")
        lastetMessageRef.setValue(chatMessage )

        val lastetMessageToRef = FirebaseDatabase.getInstance().getReference("/lastest-message/$toId/$formId")
        lastetMessageToRef.setValue(chatMessage )

    }


}