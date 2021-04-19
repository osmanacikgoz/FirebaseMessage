package com.osmanacikgoz.firebaserealtimeexample.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
    private lateinit var  binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.backtoregister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}