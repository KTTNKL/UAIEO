package com.khtn.uaieo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.Tip
import com.khtn.uaieo.model.itemAnalystUser

class AnalystUserAdapter(private val userList: ArrayList<itemAnalystUser>):
    RecyclerView.Adapter<AnalystUserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_analyst_user, parent, false);
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = userList[position]

        holder.email.text=currentItem.email
        holder.part1.text=currentItem.part1.toString()
        holder.part2.text=currentItem.part2.toString()
        holder.part3.text=currentItem.part3.toString()
        holder.part4.text=currentItem.part4.toString()
        holder.part5.text=currentItem.part5.toString()
        holder.part6.text=currentItem.part6.toString()
        holder.part7.text=currentItem.part7.toString()

    }

    override fun getItemCount(): Int {
        return userList.size;
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val email: TextView = itemView.findViewById(R.id.emailUserTV)
        val overall: TextView = itemView.findViewById(R.id.overallTV)
        val part1: TextView = itemView.findViewById(R.id.p1TV)
        val part2: TextView = itemView.findViewById(R.id.p2TV)
        val part3: TextView = itemView.findViewById(R.id.p3TV)
        val part4: TextView = itemView.findViewById(R.id.p4TV)
        val part5: TextView = itemView.findViewById(R.id.p5TV)
        val part6: TextView = itemView.findViewById(R.id.p6TV)
        val part7: TextView = itemView.findViewById(R.id.p7TV)

    }

}