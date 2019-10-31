package daniel.bastidas.data

import androidx.paging.DataSource
import daniel.bastidas.data.dbroom.MessageDao
import daniel.bastidas.data.dbroom.MessageModel
import daniel.bastidas.domain.MessagesRepository
import daniel.bastidas.domain.Message


class MessagesRepositoryImp(private val messageDao: MessageDao): MessagesRepository {

    override fun getMessages(): DataSource.Factory<Int, Message>{
        return messageDao
            .getMessages().map {
                return@map Message(it.id, it.textMessage, it.userId)
            }
    }

    override suspend fun sendMessage(message: Message)
        = messageDao.insert(
        MessageModel(0, message.textMessage, message.userId)
        )
}
