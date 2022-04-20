package com.khtn.uaieo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPartRL

class SavedQuestionAdapter(var items: ArrayList<itemPartRL>?)  : RecyclerView.Adapter<SavedQuestionAdapter.HolderView>(){
    class HolderView(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        var booktype: TextView =itemView.findViewById(R.id.examBookTypeTV)
        var examid: TextView =itemView.findViewById(R.id.examIDTV)
        var questionnumber: TextView =itemView.findViewById(R.id.examNumberTV)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedQuestionAdapter.HolderView {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val newsView = inflater.inflate(R.layout.saved_question_item, parent, false)
        return HolderView(newsView,mListenr)
    }



    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        val item=items!![position]

        var examid:String? = item.id
        var bookType:String? = item.bookType
        var questionnumber: Int? =item.number

        holder.booktype.text=bookType
        holder.examid.text=examid
        holder.questionnumber.text=questionnumber.toString()
    }

}