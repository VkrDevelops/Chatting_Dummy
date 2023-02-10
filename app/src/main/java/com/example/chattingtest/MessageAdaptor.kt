package com.example.chattingtest

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdaptor(context: Context): RecyclerView.Adapter<MyMessageViewModel>() {

    private val list= ArrayList<MessageModel>()

    fun addUser(messageModel: MessageModel){
        list.add(messageModel)
        notifyDataSetChanged()
    }
    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMessageViewModel {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.message_design,parent,false)
        return MyMessageViewModel(view)
    }

    override fun onBindViewHolder(holder: MyMessageViewModel, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()= list.size
}

class MyMessageViewModel(itemView: View) : RecyclerView.ViewHolder(itemView){
    var name=itemView.findViewById<TextView>(R.id.name)
    var message=itemView.findViewById<TextView>(R.id.message)
    var date=itemView.findViewById<TextView>(R.id.date)

    fun bind(data: MessageModel){
        if (data.senderId.equals(FirebaseAuth.getInstance().uid)){
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.purple_200))
        }
        else{
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.green))
        }
        name.text=data.name
        message.text=data.message
        date.text=data.date
    }

}
