package com.khtn.uaieo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.ExamID

class CategoryAdapter(private val category: ArrayList<String>):
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.speaking_list_item, parent, false);
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = category[position]
        holder.title.text = currentItem
    }

    override fun getItemCount(): Int {
        return category.size;
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.examTitleTV)
        init{
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }

}