package ui

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nanitsample.R
import data.models.BirthdayData
import data.models.NanitThemes
import ui.theme.BirthdayThemeColors
import utils.AgeCalculator
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BirthdayScreen(
    birthdayData: BirthdayData,
    selectedPhotoUri: Uri?,
    onPhotoClick: () -> Unit,
    onBackPress: () -> Unit = {}
) {
    //back press callback, for next interaction with server
    BackHandler {
        onBackPress()
    }
    val theme = NanitThemes.fromString(birthdayData.theme)
    //result of birthday calculation
    val ageResult = AgeCalculator.calculateAge(birthdayData.dob)

    // State to hold the measured width of the cipher row -
    // we have to calculate width of cipher and decoration set of icons, because
    //baby face circle depends on this width
    var cipherRowWidth by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getBackgroundColor(theme))
    ) {
        // Full-screen background illustration
        Image(
            painter = painterResource(id = getBackgroundDrawable(theme)),
            contentDescription = "Background illustration",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Header text: "TODAY [NAME] IS"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.today, birthdayData.name.uppercase()),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = getTextColor(theme),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.is_),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = getTextColor(theme),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(15.dp))
            // decor cipher (birthday number) with left/right
            //icons and calculate width of them for circle
            DecorForCiphers(ageResult.value,
                onWidthMeasured = { width ->
                    cipherRowWidth = width
                })
            Spacer(Modifier.height(13.dp))

            // "MONTH OLD!" or "YEAR OLD!" text
            Text(
                text = "${ageResult.unit.uppercase()}!",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = getTextColor(theme),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Photo circle with themed camera icon
            PhotoCircleWithCamera(
                selectedPhotoUri = selectedPhotoUri,
                onPhotoClick = onPhotoClick,
                theme = theme,
                cipherRowWidth
            )

            Spacer(modifier = Modifier.height(15.dp))

            // "nanit" logo
            Image(
                painter = painterResource(id = R.drawable.ic_nanit),
                contentDescription = "Nanit logo",
                modifier = Modifier
                    .height(32.dp)
                    .wrapContentWidth(),
                colorFilter = ColorFilter.tint(getTextColor(theme))
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun PhotoCircleWithCamera(
    selectedPhotoUri: Uri?,
    onPhotoClick: () -> Unit,
    theme: NanitThemes,
    circleSize : Dp
) {
    val density = LocalDensity.current
    val cameraSize = 36.dp

    //circleSize - dynamic value, we calculate when
    //recognize sizes of number and decors
    //and circle diameter depends on the size
    Box(
        modifier = Modifier.size(circleSize),
        contentAlignment = Alignment.Center
    ) {
        // Main photo circle - larger
        Box(
            modifier = Modifier
                .size(circleSize)
                .clip(CircleShape)
                .background(getCircleColor(theme))
                .border(4.dp, getCircleBorderColor(theme), CircleShape)
                .clickable { onPhotoClick() },
            contentAlignment = Alignment.Center
        ) {
            if (selectedPhotoUri != null) {
                //AsyncImage composable that executes an ImageRequest asynchronously and renders the result.
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
                // Theme-specific baby face from XML drawable - same size as inner circle
                Image(
                    painter = painterResource(id = getBabyFace(theme)),
                    contentDescription = "Baby face",

                    modifier = Modifier.size(circleSize)
                )
            }
        }

        // Themed camera icon positioned at 45°
        Box(
            modifier = Modifier
                .size(cameraSize)
                .align(Alignment.Center)
                .offset {
                    // convert dp → px
                    val radiusPx = with(density) { circleSize.toPx() } / 2
                    val camSizePx = with(density) { cameraSize.toPx() } / 2

                    val angle = PI / 4 // 45°
                    val dx = (radiusPx * cos(angle) - camSizePx).toInt()
                    val dy = (-(radiusPx * sin(angle)) - camSizePx).toInt()

                    IntOffset(dx, dy)
                }
                .size(36.dp)
                .clickable { onPhotoClick() }
        ) {
            Image(
                painter = painterResource(id = getCameraIcon(theme)),
                contentDescription = "Camera",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// Theme-based drawable selection functions
private fun getAgeDrawableId(age: Int): Int {
    //digits icons
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


private fun getCameraIcon(theme: NanitThemes): Int {
    //cam icons depends on NanitTheme
    return when (theme) {
        NanitThemes.FOX -> R.drawable.ic_fox_cam
        NanitThemes.ELEPHANT -> R.drawable.ic_elephant_cam
        NanitThemes.PELICAN -> R.drawable.ic_pelican_cam
    }
}

private fun getBabyFace(theme: NanitThemes): Int {
    //baby face icons depends on NanitTheme
    return when (theme) {
        NanitThemes.FOX -> R.drawable.ic_fox_baby_2
        NanitThemes.ELEPHANT -> R.drawable.ic_elephant_baby_2
        NanitThemes.PELICAN -> R.drawable.ic_pelikan_baby_2
    }
}


private fun getBackgroundDrawable(theme: NanitThemes): Int {
    //background drawble depends on NanitTheme
    return when (theme) {
        NanitThemes.FOX -> R.drawable.bg_fox
        NanitThemes.ELEPHANT -> R.drawable.bg_elephant
        NanitThemes.PELICAN -> R.drawable.bg_pelican
    }
}

// Theme color functions for UI elements

private fun getTextColor(theme: NanitThemes): Color {
    //text color depends on NanitTheme
    return BirthdayThemeColors.getBirthdayColors(theme).textColor
}

private fun getCircleColor(theme: NanitThemes): Color {
    //circle color depends on NanitTheme
    return BirthdayThemeColors.getBirthdayColors(theme).circleColor.copy(alpha = 0.8f)
}

private fun getCircleBorderColor(theme: NanitThemes): Color {
    //circle border color depends on NanitTheme
    return BirthdayThemeColors.getBirthdayColors(theme).circleBorderColor
}

private fun getBackgroundColor(theme: NanitThemes): Color {
    //background depends on NanitTheme
    return BirthdayThemeColors.getBirthdayColors(theme).backgroundColor
}

@Composable
private fun DecorForCiphers(age: Int,
                            onWidthMeasured: (Dp) -> Unit = {})  {
    //dynamically state for width calculation
    val ageSizes = remember(age) { ageLayoutSizes(age) }

    //Total width = 2 decors + 2 gaps + cipher width

    val totalWidth = 45.dp + ageSizes.decoGap +
            ageSizes.width +
            ageSizes.decoGap + 45.dp
    //remember static state into compose
    onWidthMeasured(totalWidth)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )  {
        // left flourish
        Image(
            painter = painterResource(R.drawable.ic_left),
            contentDescription = null,
            modifier = Modifier.size(45.dp)
        )

        Spacer(Modifier.width(ageSizes.decoGap))

        // number (kept same visual HEIGHT for all ages)
        Box(
            modifier = Modifier.size(width = ageSizes.width, height = ageSizes.height),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = getAgeDrawableId(age)),
                contentDescription = stringResource(R.string.age, age),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(Modifier.width(ageSizes.decoGap))

        // right flourish
        Image(
            painter = painterResource(R.drawable.ic_right),
            contentDescription = null,
            modifier = Modifier.size(45.dp)
        )
    }
}


private data class AgeSizes(
    val width: Dp,
    val height: Dp = 140.dp, // constant height → “same size” across 2, 8, 10, 12, etc.
    val decoGap: Dp
)

//Depends of number the icon size has different size, so we have to update decors places regarding these values

private fun ageLayoutSizes(age: Int): AgeSizes {
    //It depends on number - two ot one cipher, we have to calculate
    //width of whole number and to know position of left and right decors
    //because them must be on the same distance from the number
    //and we calculate it dynamically
    return when (age) {
        // single-digit (2..9) – same look
        in 2..9 -> AgeSizes(width = 90.dp, decoGap = 22.dp)

        // “1” is skinny → a touch more outer gap looks nicer
        1 -> AgeSizes(width = 80.dp, decoGap = 22.dp)

        // double-digit normal (10, 12) – wider box but same height
        10, 12 -> AgeSizes(width = 160.dp, decoGap = 22.dp)

        // “11” is extra narrow → increase flourish gap so it doesn’t look cramped
        11 -> AgeSizes(width = 150.dp, decoGap = 22.dp)

        else -> AgeSizes(width = 140.dp, decoGap = 22.dp)
    }
}
