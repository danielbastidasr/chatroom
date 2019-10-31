package daniel.bastidas.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * from message_table")
    suspend fun getMessages(): List<MessageModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(messageModel: MessageModel)
}