package com.khtn.uaieo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.model.ChatMessage

class ChatAdapter(private val mContext: Context, messageList: ArrayList<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val mMessageList: List<ChatMessage>
    override fun getItemCount(): Int {
        return mMessageList.size
    }

    // Determines the appropriate ViewType according to the sender of the message.
    override fun getItemViewType(position: Int): Int {
        return mMessageList[position].type
    }

    // Inflates the appropriate layout according to the ViewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == 1) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_me, parent, false)
            return SentMessageHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat, parent, false)
            return ReceivedMessageHolder(view)
        }

    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: ChatMessage = mMessageList[position]
        when (holder!!.itemViewType) {
            1 -> (holder as SentMessageHolder?)!!.bind(message)
            0 -> (holder as ReceivedMessageHolder?)!!.bind(message)
        }
    }
    private inner class SentMessageHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView
        fun bind(message: ChatMessage) {
            messageText.setText(message.content)
            timeText.setText(message.time)
        }

        init {
            messageText = itemView.findViewById<View>(R.id.text_gchat_message_me) as TextView
            timeText = itemView.findViewById<View>(R.id.text_gchat_timestamp_me) as TextView
        }
    }

    private inner class ReceivedMessageHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var email: TextView
        var content: TextView
        var date: TextView
        fun bind(message: ChatMessage) {
            content.setText(message.content)

            // Format the stored timestamp into a readable String using method.
            date.setText(message.time)
            email.setText(message.email)

        }

        init {
            email= itemView.findViewById(R.id.authorCmtTV)
            content= itemView.findViewById(R.id.contentCmtTV)
            date= itemView.findViewById(R.id.text_gchat_timestamp_other)
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }

    init {
        mMessageList = messageList
    }




}