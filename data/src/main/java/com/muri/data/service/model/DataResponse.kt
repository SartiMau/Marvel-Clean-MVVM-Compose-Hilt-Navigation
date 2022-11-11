package com.muri.data.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataResponse(
    @SerialName("data") val data: ResultResponse
) : java.io.Serializable
