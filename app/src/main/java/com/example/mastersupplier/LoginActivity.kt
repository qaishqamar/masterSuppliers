package com.example.mastersupplier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mastersupplier.models.LoginModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

open class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()
        phone_login_btn.setOnClickListener {
            val loginModel=LoginModel(this)
           val result= loginModel.LoginTextCheck(emailId_et_login,pass_et_login)
            if(result==true)
            {
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                Toast.makeText(this,"envalid email password",Toast.LENGTH_SHORT).show()
            }
        }

    }


    }
