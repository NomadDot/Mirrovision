package com.drowsynomad.mirrovision.domain.models

import com.drowsynomad.mirrovision.data.database.entities.RegularityDay

/**
 * @author Roman Voloshyn (Created on 07.08.2024)
 */

data class RegularityDay(
    val dayPosition: Int
) {
    fun toData(): RegularityDay =
        RegularityDay(dayPosition)
}