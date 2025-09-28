package ui.theme

import androidx.compose.ui.graphics.Color
import data.models.NanitThemes

data class BirthdayColors(
    val primaryColor: Color,
    val secondaryColor: Color,
    val backgroundColor: Color,
    val textColor: Color,
    val cardColor: Color,
    val accentColor: Color,
    val circleColor: Color,
    val circleBorderColor: Color,
    )

object BirthdayThemeColors {
    val pelican = BirthdayColors(
        primaryColor = Color(0xFF4A90E2),
        secondaryColor = Color(0xFF7BB3F0),
        backgroundColor = Color(0xFF2D2D2D), //#E5F6FC
        textColor = Color(0xFF2C3E50),
        cardColor = Color.White,
        accentColor = Color(0xFF87CEEB),
        circleColor = Color(0xFFB9E5EF),
        circleBorderColor = Color(0xFF8BD3E4), //#8BD3E4
    )

    val fox = BirthdayColors(
        primaryColor = Color(0xFFFF6B35),
        secondaryColor = Color(0xFFFF8C42),
        backgroundColor = Color(0xFFD3EEE7), //D3EEE7
        textColor = Color(0xFF2D2D2D),
        cardColor = Color.White,
        accentColor = Color(0xFFFFD700),
        circleColor = Color(0xFFA9DCCF),
        circleBorderColor = Color(0xFF6FC5AF), //#6FC5AF
    )

    val elephant = BirthdayColors(
        primaryColor = Color(0xFF9B59B6),
        secondaryColor = Color(0xFFBB8FCE),
        backgroundColor = Color(0xFFFEF1C4), //#FEF1C4
        textColor = Color(0xFF2D2D2D),
        cardColor = Color.White,
        accentColor = Color(0xFFE8C5E8),
        circleColor = Color(0xFFFEE7B7),
        circleBorderColor = Color(0xFFFEBE21), //#FEBE21
    )

    fun getBirthdayColors(theme: NanitThemes): BirthdayColors {
        return when (theme) {
            NanitThemes.PELICAN -> pelican
            NanitThemes.FOX -> fox
            NanitThemes.ELEPHANT -> elephant
        }
    }
}
