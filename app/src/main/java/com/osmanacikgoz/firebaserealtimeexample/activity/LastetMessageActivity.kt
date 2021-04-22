package com.osmanacikgoz.firebaserealtimeexample.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.ChatMessage
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.osmanacikgoz.firebaserealtimeexample.databinding.ActivityLastetMessageBinding
import com.osmanacikgoz.firebaserealtimeexample.viewholder.LastetMessageRow
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class LastetMessageActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }

    private lateinit var binding: ActivityLastetMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lastet_message)

        binding.recyclerLastestMessage.adapter = adapter
        binding.recyclerLastestMessage.addItemDecoration(DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener{item, view ->
            val intent = Intent(this,ChatLogActivity::class.java)

            val row = item as LastetMessageRow
            row.chatPartnerUser
            intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }
        verifyUserisLogined()
        fetchCurrentUser()
        listenForLastetMessage()
    }

    val messageMap = HashMap<String,ChatMessage>()

    private  fun refreshRecyclerViewMessage() {
        adapter.clear()
        messageMap.values.forEach{
            adapter.add(LastetMessageRow(it))
        }

    }


    private fun listenForLastetMessage() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/lastest-message/$fromId")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return
                messageMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessage()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return
                messageMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessage()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    val adapter = GroupAdapter<ViewHolder>()


    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    currentUser = snapshot.getValue(User::class.java)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


    private fun verifyUserisLogined() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.singOut -> {
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                true
            }
            R.id.newMessage -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
                true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}