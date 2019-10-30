package daniel.bastidas.domain.usecase

import daniel.bastidas.domain.MessageEntity
import daniel.bastidas.domain.MessagesRepository

class SendMessageUseCase (
    private val messagesRepository: MessagesRepository
){
    suspend fun execute(messageEntity: MessageEntity){
        return messagesRepository.sendMessage(messageEntity)
    }
}
