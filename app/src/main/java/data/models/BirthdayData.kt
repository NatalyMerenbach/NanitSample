package data.models

import com.google.gson.annotations.SerializedName

data class BirthdayData(
    @SerializedName("name")
    val name: String,
    @SerializedName("dob")
    val dob: Long,
    @SerializedName("theme")
    val theme: String
)

const val PELICAN_STR = "pelican"
const val FOX_STR = "fox"
const val ELEPHANT_STR = "elephant"

enum class NanitThemes(val value: String) {
    PELICAN(PELICAN_STR),
    FOX(FOX_STR),
    ELEPHANT(ELEPHANT_STR);

    companion object {
        fun fromString(value: String): NanitThemes {
            return NanitThemes.entries.find { it.value == value } ?: PELICAN
        }
    }
}