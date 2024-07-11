package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.core.emptyString
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

data class Category(
    val id: Long = Random.nextLong(),
    val name: String = emptyString(),
    val color: Long = 0L
)