package daniel.bastidas.domain

import java.util.*

data class Message(
    val id:Int,
    val textMessage:String,
    val userId:String,
    val time:Date
)