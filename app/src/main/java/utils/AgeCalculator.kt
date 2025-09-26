package utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object AgeCalculator {

    fun calculateAge(dobMillis: Long): AgeResult {
        val dob = Instant.ofEpochMilli(dobMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val today = LocalDate.now()

        val totalMonths = ChronoUnit.MONTHS.between(dob, today)
        val totalYears = ChronoUnit.YEARS.between(dob, today)

        return if (totalMonths < 12) {
            AgeResult(
                value = totalMonths.toInt(),
                unit = "month${if (totalMonths == 1L) "" else "s"} old",
                isInMonths = true
            )
        } else {
            AgeResult(
                value = totalYears.toInt(),
                unit = "year${if (totalYears == 1L) "" else "s"} old",
                isInMonths = false
            )
        }
    }

}

data class AgeResult(
    val value: Int,
    val unit: String,
    val isInMonths: Boolean
)