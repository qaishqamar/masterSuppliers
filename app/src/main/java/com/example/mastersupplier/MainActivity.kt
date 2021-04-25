package com.example.mastersupplier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
     lateinit var advertiseBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       advertise_layout.setOnClickListener {
            val intent=Intent(this,Advertisement::class.java)
            startActivity(intent)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        val auth=Firebase.auth
//        if(auth.currentUser==null){
//            startActivity(Intent(this,LoginActivity::class.java))
//        }
//
//    }

}