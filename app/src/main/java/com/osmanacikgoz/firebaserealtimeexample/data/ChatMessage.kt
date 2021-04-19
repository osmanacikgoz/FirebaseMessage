package com.osmanacikgoz.firebaserealtimeexample.data

data class ChatMessage(
    val id: String,
    val text: String,
    val formId: String,
    val toId: String,
    val timespam: Long
){
    constructor(): this("","","","",-1)
}
