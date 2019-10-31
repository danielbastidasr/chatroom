package daniel.bastidas.data.dbroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class MessageModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "text_message")val textMessage:String,
    @ColumnInfo(name = "user_id")val userId:String
)