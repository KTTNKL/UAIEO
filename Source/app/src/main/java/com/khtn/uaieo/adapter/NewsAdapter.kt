package com.khtn.uaieo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khtn.uaieo.R
import com.khtn.uaieo.model.NewsModel

class NewsAdapter (var context: Context
                   , private val news: ArrayList<NewsModel>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    var onItemClick : ((NewsModel)->Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val imageView: ImageView = listItemView.findViewById(R.id.imageIV)
        val title: TextView = listItemView.findViewById(R.id.titleTV)
        var url: String? = ""

        init {
            listItemView.setOnClickListener() { onItemClick?.invoke(news[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val newsView = inflater.inflate(R.layout.news_list_items, parent, false)

        return ViewHolder(newsView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemNews: NewsModel = news.get(position)
        var img = itemNews.image
        //holder.imageView.setImageResource(news.image)
        Glide.with(context).load(img).into(holder.imageView)
        holder.title.text = itemNews.title
        holder.url = itemNews.url

    }

    override fun getItemCount(): Int {
        return news.size
    }

}