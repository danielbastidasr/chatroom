package daniel.bastidas.chatroom.featurechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import daniel.bastidas.chatroom.R
import daniel.bastidas.domain.Message
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chat_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MessengerViewModel by viewModel()
    private var currentUserId:String = "1"

    private val messageListAdapter = MessageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()

        mainViewModel.listMessages
            .observe(this,
                Observer {
                    messageListAdapter.submitList(it)
                })
    }

    // SETUP VIEW
    private fun setView(){

        switchUser.setOnCheckedChangeListener { _, currentValue ->
            if(currentValue){
                switchToNormalUser()
            }
            else{
                switchToOutsider()
            }
        }

        listMessages.apply {
            adapter = messageListAdapter
            val layout = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL, true)
            layoutManager = layout

            scrollWhenItemAdded()
        }

        buttonSend.setOnClickListener {
            if(!etMessage.text.isNullOrBlank()){
                val message =
                    Message(1, etMessage.text.toString(), currentUserId, Date())
                addMessage(message)
            }
        }
    }

    // SWITCH STATES
    private fun switchToNormalUser(){
        buttonSend.setTextColor(getColor(R.color.colorAccent))
        currentUserId = "1"
    }

    private fun switchToOutsider(){
        buttonSend.setTextColor(getColor(R.color.Black))
        currentUserId = "outsider"
    }

    // ADD MESSAGE
    private fun addMessage(newMessageSent: Message){
        mainViewModel.postMessage(
            newMessageSent
        )
        etMessage.text.clear()
    }

    private fun scrollWhenItemAdded(){
        messageListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                // On insert in the first position
                if (positionStart == 0) {
                    //(Check items diff == 1) => it's a new message
                    if(itemCount == 1){
                        messageListAdapter.shouldAnimateMessage = true
                        listMessages.scrollToPosition(0)
                    }
                    else{
                        messageListAdapter.shouldAnimateMessage = false
                    }
                }
            }
        })
    }
}
