package daniel.bastidas.chatroom.featurechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import daniel.bastidas.chatroom.R
import daniel.bastidas.domain.Message
import kotlinx.android.synthetic.main.message_received_layout.view.*
import java.text.SimpleDateFormat
import java.util.*


class MessageListAdapter : PagedListAdapter<Message, MessageListAdapter.MessageViewHolder>(
    NewsDiffCallback
) {

    companion object{
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }

        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
        //TODO: Control animation with message state (sent,read,error)
        private var idAnimated = -1
    }

    private lateinit var context:Context

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null){
            holder.bind(item)
            if (position == 0){
                holder.animate()
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position)?.userId == VIEW_TYPE_MESSAGE_SENT.toString() ){
            VIEW_TYPE_MESSAGE_SENT
        } else{
            VIEW_TYPE_MESSAGE_RECEIVED
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        context = parent.context
        LayoutInflater.from(context).run {
            return when (viewType){
                VIEW_TYPE_MESSAGE_SENT -> MessageViewHolder(inflate(
                    R.layout.message_sent_layout, parent, false))
                else-> MessageViewHolder(inflate(R.layout.message_received_layout, parent, false))
            }
        }
    }

    inner class MessageViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var message: Message

        fun bind(message: Message) {
            this.message = message
            itemView.apply {
                text_message_body.text = message.textMessage
                val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formattedDate = formatter.format(message.time)
                text_message_time.text = formattedDate
            }
        }

        fun animate(){
            if(message.id != idAnimated && message.userId == VIEW_TYPE_MESSAGE_SENT.toString()){
                itemView.startAnimation(
                    AnimationUtils.loadAnimation(context,
                        R.anim.enter_from_bottom_animation
                    )
                )
                idAnimated = message.id
            }
        }
    }
}


