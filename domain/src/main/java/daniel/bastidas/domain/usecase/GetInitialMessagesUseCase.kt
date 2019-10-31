package daniel.bastidas.domain.usecase

import daniel.bastidas.domain.MessageEntity
import daniel.bastidas.domain.MessagesRepository

class GetInitialMessagesUseCase(
    private val messagesRepository: MessagesRepository
) {
    suspend fun execute(): List<MessageEntity> {
        return messagesRepository.getMessages()
    }
}