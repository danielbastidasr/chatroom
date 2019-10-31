package daniel.bastidas.chatroom.featurechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import daniel.bastidas.chatroom.R
import daniel.bastidas.domain.MessageModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                    MessageModel(1, etMessage.text.toString(), currentUserId)
                addMessage(message)
            }
        }
    }

    private fun switchToNormalUser(){
        buttonSend.setTextColor(getColor(R.color.colorAccent))
        currentUserId = "1"
    }

    private fun switchToOutsider(){
        buttonSend.setTextColor(getColor(R.color.Black))
        currentUserId = "outsider"
    }

    private fun addMessage(newMessageSent: MessageModel){
        mainViewModel.postMessage(
            newMessageSent
        )
        etMessage.text.clear()
    }

    private fun scrollWhenItemAdded(){
        messageListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    listMessages.scrollToPosition(0)
                }
            }
        })
    }
}
