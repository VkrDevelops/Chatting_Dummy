package com.example.chattingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chattingtest.SharedFunctions.addValueToSharedPref
import com.example.chattingtest.SharedFunctions.initializeSharedPreference
import com.example.chattingtest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDismissListener

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference("users")

        initializeSharedPreference(this)

//        if (SharedFunctions.getValueFromSharedPref("user").equals("")){
//            Snackbar.make(binding.root,"Need Login...",Snackbar.LENGTH_LONG).show()
//        }else{
//            Snackbar.make(binding.root,"Welcome Back...",Snackbar.LENGTH_LONG).show()
//            startActivity(Intent(this,MainActivity2::class.java))
//            finish()
//        }


        binding.login.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    addValueToSharedPref("user",binding.email.text.toString(),this)
                    startActivity(Intent(this,MainActivity2::class.java))
                }else{
                    Snackbar.make(binding.root,"Wrong Email or Password...",Snackbar.LENGTH_LONG).show()
                }
            }
        }

        binding.signup.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.email.text.trim().toString(),binding.password.text.trim().toString()).addOnCompleteListener {

                if (it.isSuccessful){
                    val data=UserModel(FirebaseAuth.getInstance().uid.toString(),binding.email.text.toString(),"dummy name",binding.password.text.toString())
                    databaseReference.child(auth.uid!!).setValue(data)
                    addValueToSharedPref("user",binding.email.text.toString(),this)
                    startActivity(Intent(this,MainActivity2::class.java))
                    Snackbar.make(binding.root,"User Created SuccessFully...",Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(binding.root,"Creation Unsuccessfully...",Snackbar.LENGTH_LONG).show()

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}