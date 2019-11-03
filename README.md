# Chat App 

<img src="screenshots/chat.gif" width="336" align="right" hspace="20">

Two way messages.

The app should have some way of being able to trigger messages from the “other” user. 
This could be done later on if we connect to external network connectivity to get messages.
At the moment we just used a switch to mimic sending and receiving messages.





# 🔌 Data Source
In this case the data provider will be the local storage which will allocate **MessagesModel** .

### The problem:
We don't want to load all the messages in a query as it could be expensive later on if we want to link it to videos, pictures and more.

### Solution: 

Room database base fortunately  give us support for paginated queries.

We used to use to query from Room as follow:

```kotlin
@Query("SELECT * from message_table ORDER BY time DESC ")
fun getMessages(): DataSource.Factory<Int, MessageModel>
```
Because the most common use case, which is to get the messages, the user just wants to see the last 10 messages. 
Obviously I ordered by the time and I reversed the order.

The class **DataSource.Factory**  is just a class that later on will help us to translate into **Livedata**.

* **[More info on: Developer Android Documentation](https://developer.android.com/topic/libraries/architecture/paging/)**


# Domain
In the domain layer I just created the repositires, here there will be more transformations if needed later on adding different data sources such as external network.

We need to make sure, we provide the Use Cases that will be needed and with their corresponding datasources.


# Presentation

### The View:
As it is a simple example, I used the Main Activity to hold the view.
I needed to create an Adapter which will help me to add Views as I needed.

It's of type PagedListAdapter, because that's what We need.
```kotlin
class MessageListAdapter : PagedListAdapter<Message, MessageListAdapter.MessageViewHolder>
```

Then to make it simple I just added 2 types of **ViewHolders**

```kotlin
override fun getItemViewType(position: Int): Int =
    if (getItem(position)?.userId == VIEW_TYPE_MESSAGE_SENT.toString() ){
        VIEW_TYPE_MESSAGE_SENT
    } else{
        VIEW_TYPE_MESSAGE_RECEIVED
    }
```

They will be identical so I just used the same class to represent them, the setup will be similar but the layouts and styles will be different.
Obviously if in the future will be more differences it will make sense to have 2 different or more. But for this example will be enough.


```kotlin
inner class MessageViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message) {
        bindViewWithInformation()
    }
}
```

### The ViewModel

Easy and self explanatory:

- Just 1 variable accessible which is the **LiveData<PagedList<Message>>**
- And one Action which is:  **fun postMessage(message: Message)**


# 🏁 Finished  
