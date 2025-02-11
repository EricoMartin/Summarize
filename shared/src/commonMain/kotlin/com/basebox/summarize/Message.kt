package com.basebox.summarize


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("content")
    val content: String = ""
)