package com.example.newsinshort.notifications

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

@JsonClass(generateAdapter = true)
data class NotificationBody(
    val title: String,
    val body: String
)
