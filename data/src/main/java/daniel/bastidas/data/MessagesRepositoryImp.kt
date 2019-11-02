package daniel.bastidas.data

import androidx.paging.DataSource
import daniel.bastidas.data.dbroom.MessageDao
import daniel.bastidas.data.dbroom.MessageModel
import daniel.bastidas.domain.MessagesRepository
import daniel.bastidas.domain.Message
import java.util.*


class MessagesRepositoryImp(private val messageDao: MessageDao): MessagesRepository {

    override fun getMessages(): DataSource.Factory<Int, Message>{
        return messageDao
            .getMessages().map {
                var time = it.time
                if(time == null){
                    time = Date()
                }
                return@map Message(it.id, it.textMessage,it.userId,time)
            }
    }

    override suspend fun sendMessage(message: Message)
        = messageDao.insert(
            MessageModel(0, message.textMessage, message.userId,message.time)
        )
}
