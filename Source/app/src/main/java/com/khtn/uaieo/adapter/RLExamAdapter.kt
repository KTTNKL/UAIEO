package com.khtn.uaieo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemExamRL
import com.khtn.uaieo.model.itemMenu

class RLExamAdapter(var items:ArrayList<itemExamRL>?) : RecyclerView.Adapter<RLExamAdapter.HolderView>(){

    class HolderView(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        var number: TextView =itemView.findViewById(R.id.testNumberTV)
        var book: TextView =itemView.findViewById(R.id.bookNameTV)
        var year: TextView =itemView.findViewById(R.id.yearTV)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RLExamAdapter.HolderView {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val newsView = inflater.inflate(R.layout.item_exam_rl, parent, false)
        return HolderView(newsView,mListenr)
    }



    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        val item=items!![position]

        var number:Int? = item.test
        var bookType:String? = item.bookType
        var year: Int? =item.year

        holder.book.text=bookType
        holder.number.text=number.toString()
        holder.year.text=year.toString()
    }
}