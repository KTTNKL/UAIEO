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

class RLPartAdapter(var items:ArrayList<Int>?) : RecyclerView.Adapter<RLPartAdapter.HolderView>(){

    class HolderView(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){

        var number: TextView =itemView.findViewById(R.id.examTitleTV)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RLPartAdapter.HolderView {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val newsView = inflater.inflate(R.layout.speaking_list_item, parent, false)
        return HolderView(newsView,mListenr)
    }



    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        val item=items!![position]
        var number:Int? = item
        holder.number.text=number.toString()
    }
}