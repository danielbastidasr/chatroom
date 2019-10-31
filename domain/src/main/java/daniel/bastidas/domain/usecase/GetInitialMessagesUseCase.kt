package daniel.bastidas.domain.usecase

import androidx.paging.DataSource
import daniel.bastidas.domain.MessageModel
import daniel.bastidas.domain.MessagesRepository

class GetInitialMessagesUseCase(
    private val messagesRepository: MessagesRepository
) {
    fun execute(): DataSource.Factory<Int,MessageModel> {
        return messagesRepository.getMessages()
    }
}