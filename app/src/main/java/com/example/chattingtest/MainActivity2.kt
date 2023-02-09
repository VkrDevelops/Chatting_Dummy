package com.example.chattingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.util.Log
import com.example.chattingtest.SharedFunctions.getValueFromSharedPref
import com.example.chattingtest.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    lateinit var database: DatabaseReference
    lateinit var user: FirebaseUser
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        database=FirebaseDatabase.getInstance().reference
        auth=FirebaseAuth.getInstance()
        user= auth.currentUser!!
        val userId=auth.uid
        val mail=user.email
//        val eMail=getValueFromSharedPref("user")
        val dT=getCurrentDateTime()
        val msg=binding.messageText.text.toString()



        binding.send.setOnClickListener {
            database.child(database.push().key).push().setValue(Message()).addOnCompleteListener {
                if (it.isSuccessful){
                    binding.messageText.findFocus()
                    binding.messageText.setText("")
                }
            }
        }
    }
    fun getCurrentDateTime(): String {
        val date = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return formatter.format(date)
    }
    private fun sendMessage(message: String) {
        database = FirebaseDatabase.getInstance().getReference("messages")
        val messageId = database.push().key
        val message = Message(messageId, message)
        database.child(messageId!!).setValue(message)
            .addOnSuccessListener {
                Log.d("waqar", "Message sent successfully")
            }
            .addOnFailureListener {
                Log.d("waqar", "Message failed to send")
            }
    }


}