package com.osmanacikgoz.firebaserealtimeexample.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.osmanacikgoz.firebaserealtimeexample.R
import com.osmanacikgoz.firebaserealtimeexample.data.User
import com.osmanacikgoz.firebaserealtimeexample.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.progress
        binding.progress.isInEditMode
        binding.progress.visibility = View.INVISIBLE
        // database = Firebase.database.reference
        //database.child("User").child("123").setValue("Osman")

        binding.regbutton.setOnClickListener {
            userRegister()
            binding.progress.visibility =View.VISIBLE
        }
        binding.loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.regselectimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }
    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            binding.regselectcicrleimage.setImageBitmap(bitmap)
            binding.regselectimage.alpha = 0f
            //  val bitmapDrawable = BitmapDrawable(bitmap)
            //  binding.regselectimage.setBackgroundDrawable(bitmapDrawable)
        }
    }
    private fun userRegister() {
        val username = binding.regusername.text.toString()
        val email = binding.regemail.text.toString()
        val password = binding.regpassword.text.toString()


        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Lütfen Kayıt İşlemini Tamamlayın", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                uploadImagetoFirebaseStorage()
            }
            .addOnFailureListener {
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImagetoFirebaseStorage() {

        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Register", "Resim Yüklendi:${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    savedUserToFirebaseDatabase(it.toString())
                }
            }

            .addOnFailureListener {

            }
    }
    private fun savedUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().reference.child("/users/$uid")
        val user = User(uid, profileImageUrl, binding.regusername.text.toString())
        // database = Firebase.database.reference
        //database.child("User").child("123").setValue("Osman")
        ref.setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, LastetMessageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}