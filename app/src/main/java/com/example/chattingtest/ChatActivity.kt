package com.example.chattingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattingtest.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    lateinit var databaseReferenceSender: DatabaseReference
    lateinit var databaseReferenceReceiver: DatabaseReference
    lateinit var adaptor: MessageAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaptor=MessageAdaptor(this)
        binding.messageRecView.adapter=adaptor
        binding.messageRecView.layoutManager=LinearLayoutManager(this)

       //..............Chat Work................//

        val receiverId=intent.getStringExtra("receiverId")

        val senderRoom=FirebaseAuth.getInstance().uid + receiverId
        val receiverRoom=receiverId + FirebaseAuth.getInstance().uid


        databaseReferenceSender=FirebaseDatabase.getInstance().getReference("chats").child(senderRoom)
        databaseReferenceReceiver=FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom)

        databaseReferenceSender.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                adaptor.clear()
                for(dataSnapShot in snapshot.children){
                    val messageModel: MessageModel=dataSnapShot.getValue(MessageModel::class.java)!!
                    adaptor.addUser(messageModel)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.send.setOnClickListener {
            if (binding.messageText.text.isNotEmpty()){
                sendMessage(binding.messageText.text.toString())
                binding.messageText.text.clear()
            }else{
                binding.messageText.error="Empty"
            }
        }

    }

    private fun sendMessage(message: String) {
        val messageId=UUID.randomUUID().toString()
        val messageModel=MessageModel(messageId,FirebaseAuth.getInstance().uid,"Dummy Name",message,getCurrentDateTime())

        adaptor.addUser(messageModel)

        databaseReferenceSender.child(messageId).setValue(messageModel)
        databaseReferenceReceiver.child(messageId).setValue(messageModel)

    }

    private fun getCurrentDateTime(): String {
        val date = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:a", Locale.getDefault())
        return formatter.format(date)
    }


}