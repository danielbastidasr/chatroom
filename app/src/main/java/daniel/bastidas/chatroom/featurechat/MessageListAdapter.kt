package daniel.bastidas.chatroom.featurechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import daniel.bastidas.chatroom.R
import daniel.bastidas.domain.Message
import kotlinx.android.synthetic.main.message_received_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.view.animation.Animation




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
        private const val DAY_HOUR_PATTERN = "EEEE HH:mm"
        private const val HOUR_PATTERN = "HH:mm"
    }

    private lateinit var context:Context
    private lateinit var parent: ViewGroup
    var shouldAnimateMessage = false

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null){
            holder.bind(item)
            if (shouldAnimateMessage && position == 0 ){
                holder.animate()
            }
            addHolderTime(holder,item,position)
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
        this.parent = parent
        LayoutInflater.from(context).run {
            return when (viewType){
                VIEW_TYPE_MESSAGE_SENT -> MessageViewHolder(inflate(
                    R.layout.message_sent_layout, parent, false))
                else-> MessageViewHolder(inflate(R.layout.message_received_layout, parent, false))
            }
        }
    }

    private fun addHolderTime(vh:MessageViewHolder,message: Message,position: Int){
        // First Message
        if (position == this.itemCount-1){
            vh.addDifferentDay()
        }
        // Check Messages hour difference
        else{
            val itemPrev = getItem(position+1)
            val prevTime = itemPrev?.time

            if (prevTime!= null){
                val hours = (message.time.time - prevTime.time )/ (1000*60*60)

                if(hours > 0){
                    vh.addDifferentDay()
                }
            }
        }
    }

    inner class MessageViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var message: Message
        private lateinit var day:TextView

        fun bind(message: Message) {
            this.message = message
            itemView.apply {
                day = time_different_day
                day.visibility = View.GONE
                text_message_body.text = message.textMessage
                val formatter = SimpleDateFormat(HOUR_PATTERN, Locale.getDefault())
                val formattedDate = formatter.format(message.time)
                text_message_time.text = formattedDate
            }
        }

        fun animate(){
            if(message.userId == VIEW_TYPE_MESSAGE_SENT.toString()){
                animateMessage()
            }
            shouldAnimateMessage = false
        }

        fun addDifferentDay(){
            val formatter = SimpleDateFormat(DAY_HOUR_PATTERN, Locale.getDefault())
            val formattedDate = formatter.format(message.time)
            day.visibility = View.VISIBLE
            day.text = formattedDate
        }


        private fun animateMessage(){
            val anim = AnimationUtils.loadAnimation(context,
                R.anim.enter_from_bottom_animation
            )
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(arg0: Animation) {
                    parent.clipToPadding = false
                }
                override fun onAnimationEnd(arg0: Animation) {
                    parent.clipToPadding = true
                }
                override fun onAnimationRepeat(arg0: Animation) {}
            })

            itemView.startAnimation(
                anim
            )
        }
    }
}


