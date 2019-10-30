package daniel.bastidas.chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import daniel.bastidas.domain.MessageEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentUserId:String = "1"
    private val messageListAdapter = MessageListAdapter()
    private val messages :List<MessageEntity> =
        listOf(
           )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
        messageListAdapter.updateData(messages)
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

                val position = messageListAdapter.addMessage(
                    MessageEntity(etMessage.text.toString() , currentUserId)
                )
                listMessages.scrollToPosition(
                    position
                )
                etMessage.text.clear()
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
}
