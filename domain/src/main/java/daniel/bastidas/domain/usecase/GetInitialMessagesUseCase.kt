package daniel.bastidas.domain.usecase

import daniel.bastidas.domain.MessageEntity
import daniel.bastidas.domain.MessagesRepository
import kotlinx.coroutines.flow.Flow


class GetInitialMessagesUseCase(
    private val messagesRepository: MessagesRepository
) {
    fun execute(): Flow<List<MessageEntity>> {
        return messagesRepository.getMessages()
    }
}