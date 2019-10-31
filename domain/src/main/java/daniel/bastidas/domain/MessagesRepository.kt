package daniel.bastidas.domain

import androidx.paging.DataSource


interface MessagesRepository {
    fun getMessages(): DataSource.Factory<Int, MessageModel>
    suspend fun sendMessage(message:MessageModel)
}
