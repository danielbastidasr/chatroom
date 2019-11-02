package daniel.bastidas.data.dbroom

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * from message_table ORDER BY time DESC ")
    fun getMessages(): DataSource.Factory<Int, MessageModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(messageModel: MessageModel)
}