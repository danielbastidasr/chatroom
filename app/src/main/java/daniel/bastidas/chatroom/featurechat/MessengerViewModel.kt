package daniel.bastidas.chatroom.featurechat

import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import daniel.bastidas.domain.usecase.GetInitialMessagesUseCase
import daniel.bastidas.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import daniel.bastidas.domain.MessageModel


internal class MessengerViewModel(
    getMessages: GetInitialMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
):ViewModel() {

    val listMessages:LiveData<PagedList<MessageModel>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(20)
            .setPageSize(10).build()

        listMessages = getMessages.execute().toLiveData(
            pagedListConfig
        )
    }

    fun postMessage(message: MessageModel) {
        viewModelScope.launch {
             sendMessageUseCase.execute(message)
        }
    }
}


