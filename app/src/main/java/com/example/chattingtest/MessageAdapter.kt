package com.example.chattingtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val list: ArrayList<MessageModel>): RecyclerView.Adapter<MyViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.message_design,parent,false)
        return MyViewModel(view)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()= list.size
}

class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView){
    val email=itemView.findViewById<TextView>(R.id.email)
    val message=itemView.findViewById<TextView>(R.id.message)
    val date=itemView.findViewById<TextView>(R.id.date)

    fun bind(data: MessageModel){
        email.text=data.email
        message.text=data.message
        date.text=data.date
    }

}
