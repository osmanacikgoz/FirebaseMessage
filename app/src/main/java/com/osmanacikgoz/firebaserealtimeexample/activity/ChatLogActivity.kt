package com.osmanacikgoz.firebaserealtimeexample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.databinding.ActivityChatLogBinding

class ChatLogActivity: AppCompatActivity() {
private  lateinit var  binding:ActivityChatLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this, R.layout.activity_chat_log)
    }
}