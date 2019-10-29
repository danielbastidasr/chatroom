package daniel.bastidas.chatroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.recyclerview.widget.RecyclerView
import daniel.bastidas.domain.Message
import kotlinx.android.synthetic.main.message_received_layout.view.*


class MessageListAdapter : RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>() {

    companion object{
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }

    private var messages:MutableList<Message> = mutableListOf()
    private lateinit var context:Context

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) = holder.bind(messages[position])

    override fun getItemViewType(position: Int): Int =
        if (messages[position].userId == VIEW_TYPE_MESSAGE_SENT.toString() ){
            VIEW_TYPE_MESSAGE_SENT
        } else{
            VIEW_TYPE_MESSAGE_RECEIVED
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MessageViewHolder{
        context = parent.context
        LayoutInflater.from(context).run {
            return when (viewType){
                VIEW_TYPE_MESSAGE_SENT-> MessageViewHolder(inflate(R.layout.message_sent_layout, parent, false))
                else-> MessageViewHolder(inflate(R.layout.message_received_layout, parent, false))
            }
        }
    }

    fun updateData(newMessages: List<Message>) {
        this.messages = newMessages.toMutableList()
        notifyDataSetChanged()
    }

    fun addMessage(newMessage:Message):Int{
        val insertAt = messages.size
        this.messages.add(insertAt,newMessage)
        notifyItemInserted(insertAt)
        return insertAt
    }

    inner class MessageViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: Message) {
            itemView.apply {
                text_message_body.text = message.textMessage
            }
            if(message.userId == VIEW_TYPE_MESSAGE_SENT.toString()){

                itemView.startAnimation(
                    AnimationUtils.loadAnimation(context,R.anim.enter_from_bottom_animation)
                )
            }
        }
    }
}


