package daniel.bastidas.data

import daniel.bastidas.domain.MessageEntity
import daniel.bastidas.domain.MessagesRepository
import kotlinx.coroutines.flow.*


class MessagesRepositoryImp: MessagesRepository {

    private val messages = mutableListOf(
        MessageEntity("initial messages", "outsider"),
        MessageEntity("initial messages", "outsider"),
        MessageEntity("initial messages", "outsider"),
        MessageEntity("initial messages", "outsider")
    )

    private val messagesFlow:Flow<List<MessageEntity>> = flow{
        emit(messages)
    }

    override fun getMessages(): Flow<List<MessageEntity>> {
        return messagesFlow
    }

    override suspend fun sendMessage(message: MessageEntity) {
        messages.add(message)
    }
}
