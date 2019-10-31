package daniel.bastidas.domain


interface MessagesRepository {
    suspend fun getMessages(): List<MessageEntity>
    suspend fun sendMessage(message:MessageEntity)
}
