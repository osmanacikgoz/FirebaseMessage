package com.osmanacikgoz.firebaserealtimeexample.viewholder

import android.widget.ImageView
import android.widget.TextView
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatViewHolderLeft(val text: String, val user: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.message_text_left).text = text
        val uri = user.profileImageUrl
        val targetImageView: ImageView = viewHolder.itemView.findViewById(R.id.message_user_image_left)
        Picasso.get().load(uri).into(targetImageView)

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}