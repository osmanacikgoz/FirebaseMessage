package com.osmanacikgoz.firebaserealtimeexample.viewholder

import android.widget.TextView
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class LastetMessageRow(val text: String, val user: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {


    }

    override fun getLayout(): Int {
        return R.layout.lastet_message_row
    }
}