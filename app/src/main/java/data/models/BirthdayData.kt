package data.models

import com.example.nanitsample.R
import com.google.gson.annotations.SerializedName

data class BirthdayData(
    @SerializedName("name")
    val name: String,
    @SerializedName("dob")
    val dob: Long,
    @SerializedName("theme")
    val theme: String
)

enum class Theme(val value: Int) {
    PELICAN(R.string.pelican),
    FOX(R.string.fox),
    ELEPHANT(R.string.elephant);

    companion object {
        fun fromString(value: Int): Theme {
            return Theme.entries.find { it.value == value } ?: PELICAN
        }
    }
}