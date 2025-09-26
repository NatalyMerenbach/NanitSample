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
                unit = "month${if (totalMonths == 1L) "" else "s"}",
                isInMonths = true
            )
        } else {
            AgeResult(
                value = totalYears.toInt(),
                unit = "year${if (totalYears == 1L) "" else "s"}",
                isInMonths = false
            )
        }
    }

    fun formatBirthDate(dobMillis: Long): String {
        val dob = Instant.ofEpochMilli(dobMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
        return dob.format(formatter)
    }

    fun getNextBirthday(dobMillis: Long): LocalDate {
        val dob = Instant.ofEpochMilli(dobMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val today = LocalDate.now()
        val thisYearBirthday = dob.withYear(today.year)

        return if (thisYearBirthday.isAfter(today) || thisYearBirthday.isEqual(today)) {
            thisYearBirthday
        } else {
            thisYearBirthday.plusYears(1)
        }
    }
}

data class AgeResult(
    val value: Int,
    val unit: String,
    val isInMonths: Boolean
)