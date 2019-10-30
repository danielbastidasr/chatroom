package daniel.bastidas.chatroom

import androidx.lifecycle.viewModelScope
import daniel.bastidas.chatroom.common.BaseAction
import daniel.bastidas.chatroom.common.BaseViewModel
import daniel.bastidas.chatroom.common.BaseViewState
import daniel.bastidas.domain.MessageEntity
import daniel.bastidas.domain.usecase.GetInitialMessagesUseCase
import daniel.bastidas.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class MessengerViewModel(
    private val getMessages: GetInitialMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
): BaseViewModel<MessengerViewModel.ViewState, MessengerViewModel.Action>(ViewState()) {

    override fun onLoadData() {
        getInitialMessageList()
    }

    override fun onReduceState(viewAction: Action): ViewState =
        when (viewAction) {
            is Action.InitialiseMessages -> {
                ViewState(viewAction.messages)
            }
        }

    fun postMessage(message:MessageEntity) {
         viewModelScope.launch {
            sendMessageUseCase.execute(message)
        }
    }

    private fun getInitialMessageList(){
        viewModelScope.launch {
            getMessages.execute().collect{ listMessages ->
                sendAction(Action.InitialiseMessages(listMessages))
            }
        }
    }

    internal data
    class ViewState(
        val listMessages:List<MessageEntity> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class InitialiseMessages(val messages: List<MessageEntity>) : Action()
    }
}


