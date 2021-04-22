package com.osmanacikgoz.firebaserealtimeexample.viewholder

import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.ChatMessage
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class LastetMessageRow(val chatMessage: ChatMessage) : Item<ViewHolder>() {
    var  chatPartnerUser :User? =null
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.last_end_message).text = chatMessage.text

        val chatPartnerId: String
        if (chatMessage.formId == FirebaseAuth.getInstance().uid) {
            chatPartnerId = chatMessage.toId
        } else {
            chatPartnerId = chatMessage.formId
        }

        val ref =FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")

        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatPartnerUser  = snapshot.getValue(User::class.java)
                viewHolder.itemView.findViewById<TextView>(R.id.last_message_username).text  =chatPartnerUser?.username

                val targetImageView = viewHolder.itemView.findViewById<ImageView>(R.id.last_message_photo)
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView )
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

    override fun getLayout(): Int {
        return R.layout.lastet_message_row
    }
}