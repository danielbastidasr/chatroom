package daniel.bastidas.data

import androidx.paging.DataSource
import daniel.bastidas.domain.MessageModel
import daniel.bastidas.domain.MessagesRepository


class MessagesRepositoryImp(private val messageDao: MessageDao): MessagesRepository {

    override fun getMessages(): DataSource.Factory<Int, MessageModel>{
        return messageDao
            .getMessages()
    }

    override suspend fun sendMessage(message: MessageModel)
        = messageDao.insert(
            MessageModel(0, message.textMessage, message.userId)
        )
}
