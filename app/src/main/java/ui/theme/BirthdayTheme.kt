package ui.theme

import androidx.compose.ui.graphics.Color
import data.models.Theme

data class BirthdayThemeColors(
    val primaryColor: Color,
    val secondaryColor: Color,
    val backgroundColor: Color,
    val textColor: Color,
    val cardColor: Color,
    val accentColor: Color,
    val circleColor: Color,
    val circleBorderColor: Color,

    )

object BirthdayThemes {
    val pelican = BirthdayThemeColors(
        primaryColor = Color(0xFF4A90E2),
        secondaryColor = Color(0xFF7BB3F0),
        backgroundColor = Color(0xFFB9E5EF),
        textColor = Color(0xFF2C3E50),
        cardColor = Color.White,
        accentColor = Color(0xFF87CEEB),
        circleColor = Color(0xFF87CEEB),
        circleBorderColor = Color(0xFF5DADE2),
    )

    val fox = BirthdayThemeColors(
        primaryColor = Color(0xFFFF6B35),
        secondaryColor = Color(0xFFFF8C42),
        backgroundColor = Color(0xFF6FC5AF), //#6FC5AF
        textColor = Color(0xFF8B4513),
        cardColor = Color.White,
        accentColor = Color(0xFFFFD700),
        circleColor = Color(0xFF9CC5A1),
        circleBorderColor = Color(0xFF6B9B7C),
    )

    val elephant = BirthdayThemeColors(
        primaryColor = Color(0xFF9B59B6),
        secondaryColor = Color(0xFFBB8FCE),
        backgroundColor = Color(0xFFFEE7B7), //#FEE7B7
        textColor = Color(0xFFFEE7B7),
        cardColor = Color.White,
        accentColor = Color(0xFFE8C5E8),
        circleColor = Color(0xFFFFD369),
        circleBorderColor = Color(0xFFE6B342),
    )

    fun getTheme(theme: Theme): BirthdayThemeColors {
        return when (theme) {
            Theme.PELICAN -> pelican
            Theme.FOX -> fox
            Theme.ELEPHANT -> elephant
        }
    }
}
