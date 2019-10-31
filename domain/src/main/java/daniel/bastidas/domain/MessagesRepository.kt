package daniel.bastidas.domain

import androidx.paging.DataSource


interface MessagesRepository {
    fun getMessages(): DataSource.Factory<Int, Message>
    suspend fun sendMessage(message: Message)
}
