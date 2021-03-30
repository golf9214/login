package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    var auth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= FirebaseAuth.getInstance()
        if(auth!!.currentUser!=null){
            val it = Intent(this,member::class.java)
            startActivity(it)
            finish()
        }
        logins.setOnClickListener {
            val email = eemail.text.toString().trim()
            val password = ppassword.text.toString().trim()
            if(email.isEmpty()){
                Toast.makeText(this,"กรุณาป้อน E-mail",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                Toast.makeText(this,"กรุณาป้อน Password",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    if(password.length<=8){
                        ppassword.error="ใส่รหัสผ่านมากกว่า 8 ตัวอักษร"
                    }else{
                        Toast.makeText(this,"Login ไม่สำเร็จ เนื่องจาก:" + task.exception!!.message,Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this,"Login สำเร็จแล้ว", Toast.LENGTH_LONG).show()
                    val  it = Intent(this, member::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }
        registers.setOnClickListener {
            val it = Intent(this,registers::class.java)
            startActivity(it)
        }
        back.setOnClickListener {
            onBackPressed()
        }
    }
}