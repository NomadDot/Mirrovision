package com.drowsynomad.mirrovision.presentation.theme

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Blue
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Brown
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Green
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Ocean
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Orange
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Pink
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Purple
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor.Red
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

val LightPrimary = Color(0xFFA58EFF)
val LightMainBackground = Color(0xFFF9F9FF)
val LightPrimaryAccentColor = Color(0xFF68509A)
val LightMainHintColor = Color(0xFFA48EFF)

val LightPrimaryInactive = Color(0xFFC0B3F0)
val MenuAccent = Color(0xFFE5D4FC)
val LightTextInactive = Color(0xFFF2F4F5)

val DarkPrimary = Color(0xFFA58EFF)
val DarkMainBackground = Color(0xFF3C3A3F)
val DarkPrimaryAccentColor = Color(0xFFA58EFF)
val DarkMainHintColor = Color(0xFFA48EFF)

val ShadowColor = Color(0xFFEDEDF3)

// Category colors
val LightBlueCategory = Color(0xFFCDDAFD)
val LightGreenCategory = Color(0xFFCADBBB)
val LightOceanBlueCategory = Color(0xFFC3ECFF)
val LightPinkCategory = Color(0xFFFBD2E2)
val LightPurpleCategory = Color(0xFFE7D5FE)
val LightOrangeCategory = Color(0xFFFFD8B9)
val LightRedCategory = Color(0xFFFFB4B9)
val LightBrownCategory = Color(0xFFC7A78E)

val DarkBlueCategory = Color(0xFF51618E)
val DarkGreenCategory = Color(0xFF71734D)
val DarkOceanBlueCategory = Color(0xFF2D6B85)
val DarkPinkCategory = Color(0xFF875669)
val DarkPurpleCategory = Color(0xFF70628C)
val DarkOrangeCategory = Color(0xFF8E6D46)
val DarkRedCategory = Color(0xFF855053)
val DarkBrownCategory = Color(0xFF735C48)

val DefaultCategoryColor = LightPurpleCategory

val LightBlueCategoryAccent = Color(0xFF001F74)
val LightGreenCategoryAccent = Color(0xFF686D00)
val LightOceanBlueCategoryAccent = Color(0xFF4A6B79)
val LightPinkCategoryAccent = Color(0xFF9B003D)
val LightPurpleCategoryAccent = Color(0xFF68509A)
val LightOrangeCategoryAccent = Color(0xFFAB4C00)
val LightRedCategoryAccent = Color(0xFF8F0009)
val LightBrownCategoryAccent = Color(0xFF78431A)

val DarkBlueCategoryAccent = Color(0xFFE3EBFF)
val DarkGreenCategoryAccent = Color(0xFFF1FFE5)
val DarkOceanBlueCategoryAccent = Color(0xFFDDF3FC)
val DarkPinkCategoryAccent = Color(0xFFFFE4EE)
val DarkPurpleCategoryAccent = Color(0xFFF1E5FF)
val DarkOrangeCategoryAccent = Color(0xFFFFEEE0)
val DarkRedCategoryAccent = Color(0xFFFFE4E5)
val DarkBrownCategoryAccent = Color(0xFFFFEFDF)

@Serializable
@Parcelize
enum class CategoryMainColor: Parcelable {
    Blue, Green, Ocean, Pink, Purple, Orange, Red, Brown;

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

private var darkModeEnabled = false
fun updateColorPalette(isDarkMode: Boolean) {
    darkModeEnabled = isDarkMode
}

fun isDarkTheme(): Boolean = darkModeEnabled

@Serializable
enum class CategoryAccentColor {
    BlueAccent, GreenAccent, OceanAccent, PinkAccent, PurpleAccent, OrangeAccent, RedAccent, BrownAccent;
}

val CategoryMainColor.pureColor: Color
    get() = getMainColor(this)

val CategoryAccentColor.pureColor: Color
    get() = getAccentColor(this)

val CategoryMainColor.accent : CategoryAccentColor
    get() = CategoryMainToAccentColors[this] ?: CategoryAccentColor.PurpleAccent


val CategoryColors by lazy { CategoryMainColor.entries.toTypedArray() }

val CategoryMainColorsMap by lazy {
    mapOf(
        Blue to LightBlueCategory,
        Green to LightGreenCategory,
        Ocean to LightOceanBlueCategory,
        Pink to LightPinkCategory,
        Purple to LightPurpleCategory,
        Orange to LightOrangeCategory,
        Red to LightRedCategory,
        Brown to LightBrownCategory,
    )
}

val DarkCategoryMainColorsMap by lazy {
    mapOf(
        Blue to DarkBlueCategory,
        Green to DarkGreenCategory,
        Ocean to DarkOceanBlueCategory,
        Pink to DarkPinkCategory,
        Purple to DarkPurpleCategory,
        Orange to DarkOrangeCategory,
        Red to DarkRedCategory,
        Brown to DarkBrownCategory,
    )
}

fun getMainColor(color: CategoryMainColor): Color {
    return if(darkModeEnabled) DarkCategoryMainColorsMap[color] ?: LightPurpleCategory
    else CategoryMainColorsMap[color] ?: DarkPurpleCategory
}

fun getAccentColor(color: CategoryAccentColor): Color {
    return if(darkModeEnabled) DarkCategoryAccentColorsMap[color] ?: LightPurpleCategoryAccent
    else CategoryAccentColorsMap[color] ?: DarkPurpleCategoryAccent
}

@Serializable
val CategoryAccentColorsMap by lazy {
    mapOf(
        CategoryAccentColor.BlueAccent to LightBlueCategoryAccent,
        CategoryAccentColor.GreenAccent to LightGreenCategoryAccent,
        CategoryAccentColor.OceanAccent to LightOceanBlueCategoryAccent,
        CategoryAccentColor.PinkAccent to LightPinkCategoryAccent,
        CategoryAccentColor.PurpleAccent to LightPurpleCategoryAccent,
        CategoryAccentColor.OrangeAccent to LightOrangeCategoryAccent,
        CategoryAccentColor.RedAccent to LightRedCategoryAccent,
        CategoryAccentColor.BrownAccent to LightBrownCategoryAccent,
    )
}

@Serializable
val DarkCategoryAccentColorsMap by lazy {
    mapOf(
        CategoryAccentColor.BlueAccent to DarkBlueCategoryAccent,
        CategoryAccentColor.GreenAccent to DarkGreenCategoryAccent,
        CategoryAccentColor.OceanAccent to DarkOceanBlueCategoryAccent,
        CategoryAccentColor.PinkAccent to DarkPinkCategoryAccent,
        CategoryAccentColor.PurpleAccent to DarkPurpleCategoryAccent,
        CategoryAccentColor.OrangeAccent to DarkOrangeCategoryAccent,
        CategoryAccentColor.RedAccent to DarkRedCategoryAccent,
        CategoryAccentColor.BrownAccent to DarkBrownCategoryAccent,
    )
}

@Serializable
val CategoryMainToAccentColors by lazy {
    mapOf(
        Blue to CategoryAccentColor.BlueAccent,
        Green to CategoryAccentColor.GreenAccent,
        Ocean to CategoryAccentColor.OceanAccent,
        Pink to CategoryAccentColor.PinkAccent,
        Purple to CategoryAccentColor.PurpleAccent,
        Orange to CategoryAccentColor.OrangeAccent,
        Red to CategoryAccentColor.RedAccent,
        Brown to CategoryAccentColor.BrownAccent
    )
}

val CategoryAccentPairsColor by lazy {
    mapOf(
        Blue to LightBlueCategoryAccent,
        Green to LightGreenCategoryAccent,
        Ocean to LightOceanBlueCategoryAccent,
        Pink to LightPinkCategoryAccent,
        Purple to LightPurpleCategoryAccent,
        Orange to LightOrangeCategoryAccent,
        Red to LightRedCategoryAccent,
        Brown to LightBrownCategoryAccent
    )
}

val GradientMain by lazy {
    if(darkModeEnabled)
        listOf(Color(0xFF8482DD), Color(0xFFE674C4))
    else
        listOf(Color(0xFFA58EFF), Color(0xFFFFC9DE))
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
    val map = if(darkModeEnabled) DarkCategoryMainColorsMap else CategoryMainColorsMap
    return map.filter { entry -> entry.value == color }.entries.first().key
}

fun convertToAccentCategoryColor(color: Color): CategoryAccentColor {
    val map = if(darkModeEnabled) DarkCategoryAccentColorsMap else CategoryAccentColorsMap
    return map.filter { entry -> entry.value == color }.entries.first().key
}