package com.osmanacikgoz.firebaserealtimeexample.viewholder

import android.widget.TextView
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView

class UserViewHolder(val user: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val userName: TextView = viewHolder.itemView.findViewById(R.id.user_name_text)
        val userImage: CircleImageView = viewHolder.itemView.findViewById(R.id.user_images)
        Picasso.get().load(user.profileImageUrl).into(userImage)
        userName.text = user.username
    }

    override fun getLayout(): Int {
        return R.layout.user_row_newmessage
    }
}