package com.khtn.uaieo.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemMenu

class MenuAdapter(var items:ArrayList<itemMenu>?) :
    RecyclerView.Adapter<MenuAdapter.HolderView>(){

    lateinit var ViewGroup: ViewGroup


    class HolderView(itemView: View): RecyclerView.ViewHolder(itemView){

        var img: ImageView =itemView.findViewById(R.id.item_home_imageView)
        var name: TextView =itemView.findViewById(R.id.feature_textView)
        var description: TextView =itemView.findViewById(R.id.caption_textView)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.HolderView {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_home,parent,false)
        ViewGroup=parent
        return MenuAdapter.HolderView(view)
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