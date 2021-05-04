package com.example.mastersupplier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mastersupplier.models.LoginModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

open class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var TextEmailId: TextInputEditText
    lateinit var TextPasswordId: TextInputEditText
    lateinit var LoginButton: Button
    lateinit var RessisterTv: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TextEmailId=findViewById(R.id.emailId_et_login)
        TextPasswordId=findViewById(R.id.pass_et_login)
        LoginButton=findViewById(R.id.phone_login_btn)


        mAuth= FirebaseAuth.getInstance()
        //FOR OOGLE SIGN IN
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)




    }

    fun ButtonClick(view: View) {
        val loginModel= LoginModel()
        when(view){
            LoginButton->{
                val result= loginModel.LoginTextCheck(TextEmailId,TextPasswordId)
                val email=emailId_et_login.text.toString()
                val password=pass_et_login.text.toString()
                Log.d("LoginActivity","login email :$email")
                Log.d("LoginActivity","login password :$password")
                if (result==true){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener {
                            val intent=Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {  }
                }
                else{
                    Toast.makeText(this,"text are not valid",Toast.LENGTH_SHORT).show()
                }

            }

            forget_pass_tv_login->{
                startActivity(Intent(this,ForgetPassword::class.java))
            }
        }
    }
    // for google sign in proce

}
