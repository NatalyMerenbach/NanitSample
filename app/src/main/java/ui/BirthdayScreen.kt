package ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nanithappybirthday.R
import data.models.BirthdayData
import data.models.Theme
import ui.theme.BirthdayThemes
import utils.AgeCalculator
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BirthdayScreen(
    birthdayData: BirthdayData,
    selectedPhotoUri: Uri?,
    onPhotoClick: () -> Unit
) {
    val theme = Theme.fromString(birthdayData.theme)
    val ageResult = AgeCalculator.calculateAge(birthdayData.dob)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getThemeBackgroundColor(theme))
    ) {
        // Full-screen background illustration
        Image(
            painter = painterResource(id = getThemeBackgroundDrawable(theme)),
            contentDescription = "Background illustration",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        // Main content overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Header text: "TODAY [NAME] IS" with automatic line wrapping
            Text(
                text = "TODAY ${birthdayData.name.uppercase()} IS",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = getThemeTextColor(theme),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Age number with decorative flourishes
            Box(
                contentAlignment = Alignment.Center
            ) {
                // Left decorative element
                Image(
                    painter = painterResource(id = R.drawable.ic_left),
                    contentDescription = "Left decoration",
                    modifier = Modifier
                        .offset(x = (-80).dp)
                        .size(40.dp)
                )

                // Stylized number based on theme and age
                Image(
                    painter = painterResource(id = getAgeDrawableId(ageResult.value)),
                    contentDescription = "Age ${ageResult.value}",
                    modifier = Modifier
                        .size(120.dp, 180.dp),
//                    colorFilter = ColorFilter.tint(getThemeAccentColor(theme))
                )

                // Right decorative element
                Image(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "Right decoration",
                    modifier = Modifier
                        .offset(x = 80.dp)
                        .size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            // "MONTH OLD!" or "YEAR OLD!" text
            Text(
                text = "${ageResult.unit.uppercase()}!",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = getThemeTextColor(theme),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Photo circle with themed camera icon
            PhotoCircleWithCamera(
                selectedPhotoUri = selectedPhotoUri,
                onPhotoClick = onPhotoClick,
                theme = theme
            )

            Spacer(modifier = Modifier.height(15.dp))

            // "nanit" logo
            Image(
                painter = painterResource(id = R.drawable.ic_nanit),
                contentDescription = "Nanit logo",
                modifier = Modifier
                    .height(32.dp)
                    .wrapContentWidth(),
                colorFilter = ColorFilter.tint(getThemeTextColor(theme))
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun PhotoCircleWithCamera(
    selectedPhotoUri: Uri?,
    onPhotoClick: () -> Unit,
    theme: Theme
) {
    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        // Main photo circle
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(getThemeCircleColor(theme))
                .border(4.dp, getThemeCircleBorderColor(theme), CircleShape)
                .clickable { onPhotoClick() },
            contentAlignment = Alignment.Center
        ) {
            if (selectedPhotoUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(selectedPhotoUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Baby photo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Theme-specific baby face from XML drawable
                Image(
                    painter = painterResource(id = getThemeBabyFace(theme)),
                    contentDescription = "Baby face",
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        // Themed camera icon positioned at 45°
        Box(
            modifier = Modifier
                .offset(
                    x = (80 * cos(PI / 4)).dp,
                    y = (-80 * sin(PI / 4)).dp
                )
                .size(36.dp)
                .clickable { onPhotoClick() }
        ) {
            Image(
                painter = painterResource(id = getThemeCameraIcon(theme)),
                contentDescription = "Camera",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// Theme-based drawable selection functions
private fun getAgeDrawableId(age: Int): Int {
    return when (age) {
        0 -> R.drawable.ic_0
        1 -> R.drawable.ic_1
        2 -> R.drawable.ic_2
        3 -> R.drawable.ic_3
        4 -> R.drawable.ic_4
        5 -> R.drawable.ic_5
        6 -> R.drawable.ic_6
        7 -> R.drawable.ic_7
        8 -> R.drawable.ic_8
        9 -> R.drawable.ic_9
        10 -> R.drawable.ic_10
        11 -> R.drawable.ic_11
        12 -> R.drawable.ic_12
        else -> R.drawable.ic_1 // fallback
    }
}

private fun getThemeCameraIcon(theme: Theme): Int {
    return when (theme) {
        Theme.FOX -> R.drawable.ic_fox_cam
        Theme.ELEPHANT -> R.drawable.ic_elephant_cam
        Theme.PELICAN -> R.drawable.ic_pelican_cam
    }
}

private fun getThemeBabyFace(theme: Theme): Int {
    return when (theme) {
        Theme.FOX -> R.drawable.ic_fox_baby_2
        Theme.ELEPHANT -> R.drawable.ic_elephant_baby_2
        Theme.PELICAN -> R.drawable.ic_pelikan_baby_2
    }
}

private fun getThemeBabyFaceSmall(theme: Theme): Int {
    return when (theme) {
        Theme.FOX -> R.drawable.ic_fox_baby_3
        Theme.ELEPHANT -> R.drawable.ic_elephant_baby_3
        Theme.PELICAN -> R.drawable.ic_pelikan_baby_3
    }
}

private fun getThemeBackgroundDrawable(theme: Theme): Int {
    return when (theme) {
        Theme.FOX -> R.drawable.bg_fox
        Theme.ELEPHANT -> R.drawable.bg_elephant
        Theme.PELICAN -> R.drawable.bg_pelican
    }
}

// Theme color functions for UI elements

private fun getThemeTextColor(theme: Theme): Color {
    return when (theme) {
        Theme.FOX -> Color(0xFF2D2D2D) // Dark gray for better contrast
        Theme.ELEPHANT -> Color(0xFF2D2D2D)
        Theme.PELICAN -> Color(0xFF2D2D2D)
    }
}

private fun getThemeAccentColor(theme: Theme): Color {
    return when (theme) {
        Theme.FOX -> Color(0xFFE85A4F) // Coral red as shown in Figma
        Theme.ELEPHANT -> Color(0xFFE85A4F)
        Theme.PELICAN -> Color(0xFFE85A4F)
    }
}

private fun getThemeCircleColor(theme: Theme): Color {
    return BirthdayThemes.getTheme(theme).textColor
}

private fun getThemeCircleBorderColor(theme: Theme): Color {
    return when (theme) {
        Theme.FOX -> Color(0xFF6B9B7C) // Darker green border
        Theme.ELEPHANT -> Color(0xFFE6B342) // Darker yellow border
        Theme.PELICAN -> Color(0xFF5DADE2) // Darker blue border
    }
}


private fun getThemeBackgroundColor(theme: Theme): Color {
    return BirthdayThemes.getTheme(theme).backgroundColor
//    return when (theme) {
//        Theme.FOX -> BirthdayThemes.getTheme(Theme.FOX).backgroundColor // Light green to fill transparent areas
//        Theme.ELEPHANT -> Color(0xFFFFF8DC) // Light cream to fill transparent areas
//        Theme.PELICAN -> Color(0xFFE3F2FD) // Light blue to fill transparent areas
//    }
}

