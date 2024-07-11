package com.drowsynomad.mirrovision.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R

private val mainFonts = FontFamily(
    Font(R.font.ibm_cursive, FontWeight.ExtraLight),
    Font(R.font.ibm_light, FontWeight.Light),
    Font(R.font.ibm_medium, FontWeight.Medium),
    Font(R.font.ibm_semibold, FontWeight.SemiBold),
    Font(R.font.ibm_semibold, FontWeight.Bold)
)

val MirrovisionTypography = Typography(
    bodyMedium = TextStyle(
        color = LightPrimaryAccentColor,
        fontSize = 14.sp,
        fontFamily = mainFonts,
        fontWeight = FontWeight.Medium,
    ),
    titleSmall = TextStyle(
        color = LightPrimaryAccentColor,
        fontSize = 16.sp,
        fontFamily = mainFonts,
        fontWeight = FontWeight.SemiBold,
    ),
    titleMedium = TextStyle(
        color = LightPrimaryAccentColor,
        fontSize = 24.sp,
        fontFamily = mainFonts,
        fontWeight = FontWeight.Bold,
    ),
    labelSmall = TextStyle(
        color = LightMainHintColor,
        fontSize = 14.sp,
        fontFamily = mainFonts,
        fontWeight = FontWeight.Light,
    ),
    headlineSmall = TextStyle(
        color = LightPrimary,
        fontSize = 12.sp,
        fontFamily = mainFonts,
        fontWeight = FontWeight.ExtraLight,
    ),)