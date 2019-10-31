package daniel.bastidas.chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import daniel.bastidas.domain.MessageEntity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainviewModel: MessengerViewModel by viewModel()

    private var currentUserId:String = "1"

    private val messageListAdapter = MessageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()

        mainviewModel.stateLiveData
            .observe(this,
                Observer {
                    messageListAdapter.updateData(it.listMessages)
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
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        buttonSend.setOnClickListener {
            if(!etMessage.text.isNullOrBlank()){
                val message = MessageEntity(etMessage.text.toString() , currentUserId)
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

    private fun addMessage(newMessageSent:MessageEntity){
        mainviewModel.postMessage(
            newMessageSent
        )
        val position = messageListAdapter.addMessage(
            newMessageSent
        )
        listMessages.scrollToPosition(
            position
        )
        etMessage.text.clear()
    }
}
