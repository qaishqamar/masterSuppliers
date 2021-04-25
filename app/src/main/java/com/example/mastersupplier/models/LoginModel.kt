package com.example.mastersupplier.models

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.EditText
import com.example.mastersupplier.LoginActivity
import com.example.mastersupplier.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class LoginModel(val context:Context): LoginActivity() {
    var strEmail:String=""
    var strPassword=""
    fun LoginTextCheck(email: EditText, pass:EditText):Boolean{
             strEmail=email.text.toString().trim()
             strPassword= pass.text.toString().trim()
            return emailCheck(email,pass)

        }
//        fun emailVlidation(strEmail:String):Boolean{
//            val EMAIL_PATTERN="^[_A-Za-z0-9-]+(\\.[_A-za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
//
//            val pattern= Pattern.compile(EMAIL_PATTERN)
//            val matcher: Matcher =pattern.matcher(strEmail)
//            return matcher.matches()
//        }
    fun emailCheck(email: EditText,pass: EditText):Boolean{
        val arrayPasEm= arrayOf("qaishsupplier@gmail.com","assqcovid19@gmail.com")

       for (i in 0..arrayPasEm.size-1){
           if(arrayPasEm[i]==strEmail){
              return passChek(pass)

           }

        }
    email.error="envalid email"
    return false

    }
    fun passChek(pass_et: EditText):Boolean{
        val pass="QS12@"
        if (strPassword==pass){
            return true
        }
        pass_et.error="wrong password"
        return false
    }
}
