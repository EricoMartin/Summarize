package com.basebox.summarize


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AIResponse(
    @SerialName("choices")
    val choices: List<Choice> = listOf(),
    @SerialName("created")
    val created: Int = 0,
    @SerialName("id")
    val id: String = "",
    @SerialName("model")
    val model: String = "",
    @SerialName("object")
    val objectX: String = "",
    @SerialName("usage")
    val usage: Usage = Usage()
)