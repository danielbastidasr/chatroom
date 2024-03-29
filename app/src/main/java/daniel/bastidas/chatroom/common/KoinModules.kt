package daniel.bastidas.chatroom.common


import daniel.bastidas.chatroom.featurechat.MessengerViewModel
import daniel.bastidas.data.dbroom.MessageRoomDatabase
import daniel.bastidas.data.MessagesRepositoryImp
import daniel.bastidas.domain.MessagesRepository
import daniel.bastidas.domain.usecase.GetInitialMessagesUseCase
import daniel.bastidas.domain.usecase.SendMessageUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MessengerViewModel(get(), get())
    }
}

val domainModule = module {
    factory {
        GetInitialMessagesUseCase(messagesRepository = get())
    }
    factory {
        SendMessageUseCase(messagesRepository = get())
    }
}

val dataModule = module {

    single {
        MessageRoomDatabase.getDatabase(context = androidApplication())
    }

    single{
        get<MessageRoomDatabase>().messageDao()
    }

    single {
        MessagesRepositoryImp(messageDao = get()) as MessagesRepository
    }

}

val koinModules = listOf(
    dataModule,
    domainModule,
    presentationModule
)