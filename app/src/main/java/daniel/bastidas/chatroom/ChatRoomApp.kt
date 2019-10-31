package daniel.bastidas.chatroom

import android.app.Application
import daniel.bastidas.chatroom.common.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChatRoomApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChatRoomApp.applicationContext)
            modules(koinModules)
        }
    }
}