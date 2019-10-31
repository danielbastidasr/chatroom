package daniel.bastidas.domain.usecase

import daniel.bastidas.domain.MessageModel
import daniel.bastidas.domain.MessagesRepository

class SendMessageUseCase (
    private val messagesRepository: MessagesRepository
){
    suspend fun execute(messageEntity: MessageModel){
        return messagesRepository.sendMessage(messageEntity)
    }
}
