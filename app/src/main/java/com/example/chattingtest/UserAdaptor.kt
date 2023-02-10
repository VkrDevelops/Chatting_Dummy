package com.example.chattingtest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdaptor(context: Context, val onCLick: (model: AdaptorPositionModel)->Unit): RecyclerView.Adapter<MyViewModel>() {

    private val list= ArrayList<UserModel>()

    fun addUser(userModel: UserModel){
        list.add(userModel)
        notifyDataSetChanged()
    }
    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_design,parent,false)
        return MyViewModel(view)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.bind(list[position],onCLick)
    }

    override fun getItemCount()= list.size
}

class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView){
    var email=itemView.findViewById<TextView>(R.id.userEmail)
    var name=itemView.findViewById<TextView>(R.id.userName)

    fun bind(data: UserModel, onCLick: (model: AdaptorPositionModel)->Unit){
        email.text=data.email
        name.text=data.name
        itemView.setOnClickListener {
//            val intent=Intent(itemView.context,ChatActivity::class.java)
//            intent.putExtra("receiverId", data.id.toString())

            val model =AdaptorPositionModel(adapterPosition,data.id.toString())
            onCLick(model)
        }
    }

}
