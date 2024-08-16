package com.drowsynomad.mirrovision.presentation.theme

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightPrimary = Color(0xFFA58EFF)
val LightMainBackground = Color(0xFFF9F9FF)
val LightPrimaryAccentColor = Color(0xFF68509A)
val LightMainHintColor = Color(0xFFA48EFF)

val LightPrimaryInactive = Color(0xFFC0B3F0)
val MenuAccent = Color(0xFFE5D4FC)
val LightTextInactive = Color(0xFFF2F4F5)

val ShadowColor = Color(0xFFEDEDF3)

// Category colors
val BlueCategory = Color(0xFFCDDAFD)
val GreenCategory = Color(0xFFCADBBB)
val YellowCategory = Color(0xFFFBF8CB)
val PinkCategory = Color(0xFFFBD2E2)
val PurpleCategory = Color(0xFFE7D5FE)
val OrangeCategory = Color(0xFFFFD8B9)
val RedCategory = Color(0xFFFFB4B9)
val BrownCategory = Color(0xFFC7A78E)

val DefaultCategoryColor = PurpleCategory

val BlueCategoryAccent = Color(0xFF001F74)
val GreenCategoryAccent = Color(0xFF686D00)
val YellowCategoryAccent = Color(0xFFA07300)
val PinkCategoryAccent = Color(0xFF9B003D)
val PurpleCategoryAccent = Color(0xFF68509A)
val OrangeCategoryAccent = Color(0xFFAB4C00)
val RedCategoryAccent = Color(0xFF8F0009)
val BrownCategoryAccent = Color(0xFF78431A)

@Serializable
@Parcelize
enum class CategoryMainColor: Parcelable {
    Blue, Green, Yellow, Pink, Purple, Orange, Red, Brown;
    @IgnoredOnParcel
    val pureColor  by lazy { CategoryMainColorsMap[this] ?: BlueCategory }
    @IgnoredOnParcel
    val accent by lazy { CategoryMainToAccentColors[this] ?: CategoryAccentColor.BlueAccent }

    companion object {
        fun parse(stringValue: String): CategoryMainColor = let {
            entries.toList().forEach { color ->
                if(color.toString() == stringValue)
                    return@let color
            }
            return@let Purple
        }
    }
}

@Serializable
enum class CategoryAccentColor {
    BlueAccent, GreenAccent, YellowAccent, PinkAccent, PurpleAccent, OrangeAccent, RedAccent, BrownAccent;
    val pureColor by lazy { CategoryAccentColorsMap[this] ?: BlueCategoryAccent }
}

val CategoryColors by lazy { CategoryMainColor.entries.toTypedArray() }

val CategoryMainColorsMap by lazy {
    mapOf(
        Blue to BlueCategory,
        Green to GreenCategory,
        Yellow to YellowCategory,
        Pink to PinkCategory,
        Purple to PurpleCategory,
        Orange to OrangeCategory,
        Red to RedCategory,
        Brown to BrownCategory,
    )
}

@Serializable
val CategoryAccentColorsMap by lazy {
    mapOf(
        CategoryAccentColor.BlueAccent to BlueCategoryAccent,
        CategoryAccentColor.GreenAccent to GreenCategoryAccent,
        CategoryAccentColor.YellowAccent to YellowCategoryAccent,
        CategoryAccentColor.PinkAccent to PinkCategoryAccent,
        CategoryAccentColor.PurpleAccent to PurpleCategoryAccent,
        CategoryAccentColor.OrangeAccent to OrangeCategoryAccent,
        CategoryAccentColor.RedAccent to RedCategoryAccent,
        CategoryAccentColor.BrownAccent to BrownCategoryAccent,
    )
}

@Serializable
val CategoryMainToAccentColors by lazy {
    mapOf(
        Blue to CategoryAccentColor.BlueAccent,
        Green to CategoryAccentColor.GreenAccent,
        Yellow to CategoryAccentColor.YellowAccent,
        Pink to CategoryAccentColor.PinkAccent,
        Purple to CategoryAccentColor.PurpleAccent,
        Orange to CategoryAccentColor.OrangeAccent,
        Red to CategoryAccentColor.RedAccent,
        Brown to CategoryAccentColor.BrownAccent
    )
}

val CategoryAccentPairsColor by lazy {
    mapOf(
        Blue to BlueCategoryAccent,
        Green to GreenCategoryAccent,
        Yellow to YellowCategoryAccent,
        Pink to PinkCategoryAccent,
        Purple to PurpleCategoryAccent,
        Orange to OrangeCategoryAccent,
        Red to RedCategoryAccent,
        Brown to BrownCategoryAccent
    )
}

val GradientMain by lazy {
    listOf(
        Color(0xFFA58EFF),
        Color(0xFFFFC9DE)
    )
}

val GradientButtonColors by lazy {
    listOf(
        Color(0xFF8567FA),
        Color(0xFF7755FA),
        Color(0xFFEE1683),
        Color(0xFFEE4098),
    )
}

val GradientAccent by lazy {
    listOf(
        Color(0xFF8161FF),
        Color(0xFFEE4098)
    )
}

fun convertToMainCategoryColor(color: Color): CategoryMainColor {
    return CategoryMainColorsMap.filter { entry -> entry.value == color }.entries.first().key
}

fun convertToAccentCategoryColor(color: Color): CategoryAccentColor {
    return CategoryAccentColorsMap.filter { entry -> entry.value == color }.entries.first().key
}