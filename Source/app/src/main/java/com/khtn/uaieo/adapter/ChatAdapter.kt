package com.khtn.uaieo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.ChatMessage
import com.khtn.uaieo.model.Comment

class ChatAdapter(private val chatList: ArrayList<ChatMessage>):
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false);
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = chatList[position]
        holder.email.text = currentItem.email
        holder.content.text = currentItem.content
        holder.date.text = currentItem.time

    }

    override fun getItemCount(): Int {
        return chatList.size;
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var email: TextView = itemView.findViewById(R.id.emailChatTV)
        var content: TextView = itemView.findViewById(R.id.contentChatTV)
        var date: TextView= itemView.findViewById(R.id.dateTV)
    }

}