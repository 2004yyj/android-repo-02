package com.woowahan.data.entity

import com.woowahan.domain.model.Message

data class MessageData(
    val message: String
)

fun MessageData.toModel(): Message {
    return Message(this.message)
}