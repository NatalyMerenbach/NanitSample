package utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object AgeCalculator {

}

data class AgeResult(
    val value: Int,
    val unit: String,
    val isInMonths: Boolean
)