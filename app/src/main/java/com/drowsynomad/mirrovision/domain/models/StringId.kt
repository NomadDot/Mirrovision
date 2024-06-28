package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.core.emptyString
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 28.06.2024)
 */

@Serializable
data class StringId(
    val id: String = emptyString()
)