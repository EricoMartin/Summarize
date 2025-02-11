package com.basebox.summarize


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usage(
    @SerialName("completion_tokens")
    val completionTokens: Int = 0,
    @SerialName("prompt_tokens")
    val promptTokens: Int = 0,
    @SerialName("total_tokens")
    val totalTokens: Int = 0
)