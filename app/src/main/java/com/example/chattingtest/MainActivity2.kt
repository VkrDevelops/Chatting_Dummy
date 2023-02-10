package com.example.chattingtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattingtest.SharedFunctions.addValueToSharedPref
import com.example.chattingtest.SharedFunctions.initializeSharedPreference
import com.example.chattingtest.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var userModel: UserModel
    private lateinit var adaptor : UserAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeSharedPreference(this)


        adaptor=UserAdaptor(this){

            intent.putExtra("receiverId", it.id)
            startActivity(Intent(this,ChatActivity::class.java))

        }

        binding.messageRecView.adapter=adaptor
        binding.messageRecView.layoutManager=LinearLayoutManager(this)

        database = FirebaseDatabase.getInstance().getReference("users")
        auth = FirebaseAuth.getInstance()


        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = auth.uid
                adaptor.clear()
                for (dataSnapShot in snapshot.children) {
                    val userId = dataSnapShot.key
                    if (userId != null) {
                        if (userId != user) {
                            userModel= dataSnapShot.getValue(UserModel::class.java)!!
                            adaptor.addUser(userModel)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.logout.setOnClickListener{
            auth.signOut()
            addValueToSharedPref("user","",this)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }


}