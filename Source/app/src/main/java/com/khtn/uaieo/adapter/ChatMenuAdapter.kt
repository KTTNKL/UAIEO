package com.khtn.uaieo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemMenu

class ChatMenuAdapter(var items:ArrayList<itemMenu>?) :
    RecyclerView.Adapter<ChatMenuAdapter.HolderView>(){



    class HolderView(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        var img: ImageView =itemView.findViewById(R.id.item_home_imageView)
        var name: TextView =itemView.findViewById(R.id.feature_textView)
        var description: TextView =itemView.findViewById(R.id.caption_textView)
        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
    private lateinit var mListenr: onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int){

        }
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListenr=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMenuAdapter.HolderView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false);
        return ChatMenuAdapter.HolderView(itemView,mListenr)
    }



    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        val item=items!![position]

        var name:String? = item.name
        var description:String? = item.description
        var img: Int=item.image

        holder.img.setImageResource(img)
        holder.name.text=name
        holder.description.text=description
    }
}