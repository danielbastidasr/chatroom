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
import daniel.bastidas.domain.MessageModel
import kotlinx.android.synthetic.main.message_received_layout.view.*


class MessageListAdapter : PagedListAdapter<MessageModel, MessageListAdapter.MessageViewHolder>(
    NewsDiffCallback
) {

    companion object{
        val NewsDiffCallback = object : DiffUtil.ItemCallback<MessageModel>() {
            override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
                return oldItem == newItem
            }
        }

        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
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

        private lateinit var message: MessageModel

        fun bind(message: MessageModel) {
            this.message = message
            itemView.apply {
                text_message_body.text = message.textMessage
            }
        }

        fun animate(){
            if(message.userId == VIEW_TYPE_MESSAGE_SENT.toString()){
                itemView.startAnimation(
                    AnimationUtils.loadAnimation(context,
                        R.anim.enter_from_bottom_animation
                    )
                )
            }
        }
    }
}


