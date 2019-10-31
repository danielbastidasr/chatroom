package daniel.bastidas.data

import daniel.bastidas.domain.MessageEntity
import daniel.bastidas.domain.MessagesRepository


class MessagesRepositoryImp(private val messageDao: MessageDao): MessagesRepository {

    override suspend fun getMessages(): List<MessageEntity>
        = messageDao
            .getMessages().map {
             message->
                MessageEntity(
                    message.textMessage,
                    message.userId
                )
            }


    override suspend fun sendMessage(message: MessageEntity)
        = messageDao.insert(
            MessageModel(
                0,
                message.textMessage,
                message.userId
            )
        )

}
