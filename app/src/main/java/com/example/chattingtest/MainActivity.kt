package com.example.chattingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chattingtest.SharedFunctions.addValueToSharedPref
import com.example.chattingtest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDismissListener

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        SharedFunctions.initializeSharedPreference(this)

        if (SharedFunctions.getValueFromSharedPref("user").equals("")){
            Snackbar.make(binding.root,"Need Login...",Snackbar.LENGTH_LONG).show()
        }else{
            Snackbar.make(binding.root,"Welcome Back...",Snackbar.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity2::class.java))
            finish()
        }


        binding.login.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    startActivity(Intent(this,MainActivity2::class.java))
                    addValueToSharedPref("user",binding.email.text.toString(),this)
                }else{
                    Snackbar.make(binding.root,"Wrong Email or Password...",Snackbar.LENGTH_LONG).show()
                }
            }
        }

        binding.signup.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString()).addOnCompleteListener {

                if (it.isSuccessful){
                    Snackbar.make(binding.root,"User Created SuccessFully...",Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(binding.root,"Creation Unsuccessfully...",Snackbar.LENGTH_LONG).show()

                }
            }
        }
    }
}