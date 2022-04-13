package com.khtn.uaieo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView;
import com.khtn.uaieo.R
import com.khtn.uaieo.model.Comment


class CommentAdapter(private val ExamList: ArrayList<Comment>):
    RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false);
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = ExamList[position]
        //holder.title.text = currentItem.id
        holder.author.text = currentItem.email + ":"
        holder.content.text = currentItem.content
    }

    override fun getItemCount(): Int {
        return ExamList.size;
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        val author: TextView = itemView.findViewById(R.id.authorCmtTV)
        val content: TextView = itemView.findViewById(R.id.contentCmtTV)
        init{
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }

}