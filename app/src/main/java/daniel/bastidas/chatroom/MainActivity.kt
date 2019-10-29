package daniel.bastidas.chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import daniel.bastidas.domain.Message
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val messageListAdapter = MessageListAdapter()
    private val messages :List<Message> =
        listOf(
            Message("texto 1","2545245"),
            Message("texto 2","2244522222"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
        messageListAdapter.updateData(messages)
    }

    private fun setView(){
        listMessages.apply {
            adapter = messageListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        buttonSend.setOnClickListener {
            if(!etMessage.text.isNullOrBlank()){

                val position = messageListAdapter.addMessage(
                    Message(etMessage.text.toString() , "1")
                )
                listMessages.scrollToPosition(
                    position
                )
                etMessage.text.clear()
            }
        }
    }
}
