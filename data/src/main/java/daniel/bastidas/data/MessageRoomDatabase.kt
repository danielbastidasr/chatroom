package daniel.bastidas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import daniel.bastidas.domain.MessageModel

@Database(entities = arrayOf(MessageModel::class), version = 1, exportSchema = false)
abstract class MessageRoomDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MessageRoomDatabase? = null

        fun getDatabase(context: Context): MessageRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageRoomDatabase::class.java,
                    "chat_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
