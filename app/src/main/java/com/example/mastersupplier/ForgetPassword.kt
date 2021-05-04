package com.example.mastersupplier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mastersupplier.models.LoginModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        btn_forget_submit.setOnClickListener {
            val email=emailId_et_forget.text.toString()
            val loginModel= LoginModel()
            if (loginModel.emailVlidation(email)==false){
                emailId_et_forget.error="Email is not valid"
                Toast.makeText(this,"wrong email", Toast.LENGTH_SHORT).show()
            }
            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this,"reset mail sent succesfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            Toast.makeText(this,"${task.exception}", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

            }
        }
    }
}