package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.reflect.Member

class register : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        if(auth!!.currentUser!=null){
            val it = Intent(this, member::class.java)
            startActivity(it)
            finish()
        }
        registers.setOnClickListener {
            val email = edtemail.text.toString().trim()
            val epassword = edtpassword.text.toString().trim()

            if (email.isEmpty()){
                Toast.makeText(this,"กรุณาป้อน Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (epassword.isEmpty()){
                Toast.makeText(this,"กรุณาป้อน Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth!!.createUserWithEmailAndPassword(email,epassword).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    if (epassword.length<=8){
                        edtpassword.error="ใส่รหัสผ่านมากกว่า 8 ตัวอักษร"
                    }else{
                        Toast.makeText(this,"Login ไม่สำเร็จ เนื่องจาก:"+task.exception!!.message,Toast.LENGTH_LONG).show()
                    }
                    edtemail.setText("")
                    edtpassword.setText("")
                }else{
                    Toast.makeText(this,"Login สำเร็จ",Toast.LENGTH_LONG).show()
                    val  it = Intent(this, Member::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }
        login.setOnClickListener {
            val it = Intent(this, login::class.java)
            startActivity(it)
        }
    }
}
