package daniel.bastidas.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.bastidas.domain.MessageModel

@Dao
interface MessageDao {

    @Query("SELECT * from message_table ORDER BY id DESC ")
    fun getMessages(): DataSource.Factory<Int, MessageModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(messageModel: MessageModel)
}