package daniel.bastidas.domain.usecase

import daniel.bastidas.domain.Message
import daniel.bastidas.domain.MessagesRepository

class SendMessageUseCase (
    private val messagesRepository: MessagesRepository
){
    suspend fun execute(messageEntity: Message){
        return messagesRepository.sendMessage(messageEntity)
    }
}
