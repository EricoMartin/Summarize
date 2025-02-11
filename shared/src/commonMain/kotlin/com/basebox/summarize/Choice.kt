package com.basebox.summarize


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    @SerialName("finish_reason")
    val finishReason: String = "",
    @SerialName("index")
    val index: Int = 0,
    @SerialName("message")
    val message: Message = Message()
)