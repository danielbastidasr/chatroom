package daniel.bastidas.domain

import kotlinx.coroutines.flow.Flow


interface MessagesRepository {
    fun getMessages(): Flow<List<MessageEntity>>
    suspend fun sendMessage(message:MessageEntity)
}
