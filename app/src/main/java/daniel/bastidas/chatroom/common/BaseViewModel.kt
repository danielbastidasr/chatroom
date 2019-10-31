package daniel.bastidas.chatroom.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData:LiveData<ViewState> = stateMutableLiveData

    init {
        loadData()
    }

    protected fun sendAction(viewAction: ViewAction) {
        stateMutableLiveData.postValue(onReduceState(viewAction))
    }

    private fun loadData() {
        onLoadData()
    }

    protected open fun onLoadData() {}

    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}

interface BaseAction
interface BaseViewState