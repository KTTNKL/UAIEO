package com.khtn.uaieo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView;
import com.khtn.uaieo.R
import com.khtn.uaieo.model.Tip

class TipAdapter(private val tipList: ArrayList<Tip>):
    RecyclerView.Adapter<TipAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tip_list_items, parent, false);
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = tipList[position]
        holder.title.text = currentItem.title
        holder.subtitle.text = currentItem.subtitle


    }

    override fun getItemCount(): Int {
        return tipList.size;
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.tipTitleTV)
        val subtitle: TextView = itemView.findViewById(R.id.subtitleTV)


        init{
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }

}