package com.osmanacikgoz.firebaserealtimeexample.viewholder

import android.widget.TextView
import com.osmanacikgoz.firebaserealtimeexample.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatViewHolder (val text: String) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.message_text).text = text

    }

    override fun getLayout(): Int {
    return  R.layout.chat_from_row
    }
}