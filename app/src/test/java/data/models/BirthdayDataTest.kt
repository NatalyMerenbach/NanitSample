package data.models

import org.junit.Test
import org.junit.Assert.*

class BirthdayDataTest {

    @Test
    fun `BirthdayData creates correctly with valid parameters`() {
        // Given
        val name = "Nanit"
        val dob = 1685826000000L
        val theme = "pelican"

        // When
        val birthdayData = BirthdayData(name = name, dob = dob, theme = theme)

        // Then
        assertEquals(name, birthdayData.name)
        assertEquals(dob, birthdayData.dob)
        assertEquals(theme, birthdayData.theme)
    }

    @Test
    fun `Theme fromString returns correct enum for valid pelican`() {
        // Given
        val themeString = "pelican"

        // When
        val theme = Theme.fromString(themeString)

        // Then
        assertEquals(Theme.PELICAN, theme)
        assertEquals(PELICAN_STR, theme.value)
    }


    @Test
    fun `Theme fromString returns PELICAN as default for invalid theme`() {
        // Given
        val invalidTheme = "invalid_theme"

        // When
        val theme = Theme.fromString(invalidTheme)

        // Then
        assertEquals(Theme.PELICAN, theme)
    }

    @Test
    fun `Theme fromString returns PELICAN as default for empty string`() {
        // Given
        val emptyTheme = ""

        // When
        val theme = Theme.fromString(emptyTheme)

        // Then
        assertEquals(Theme.PELICAN, theme)
    }

    @Test
    fun `Theme fromString handles case sensitivity correctly`() {
        // Given
        val uppercaseTheme = "PELICAN"
        val mixedCaseTheme = "Fox"

        // When
        val upperResult = Theme.fromString(uppercaseTheme)
        val mixedResult = Theme.fromString(mixedCaseTheme)

        // Then
        assertEquals("Uppercase should default to PELICAN", Theme.PELICAN, upperResult)
        assertEquals("Mixed case should default to PELICAN", Theme.PELICAN, mixedResult)
    }

    @Test
    fun `Theme constants match enum values`() {
        // Given/When/Then
        assertEquals("pelican", PELICAN_STR)
        assertEquals("fox", FOX_STR)
        assertEquals("elephant", ELEPHANT_STR)

        assertEquals(PELICAN_STR, Theme.PELICAN.value)
        assertEquals(FOX_STR, Theme.FOX.value)
        assertEquals(ELEPHANT_STR, Theme.ELEPHANT.value)
    }

    @Test
    fun `Theme enum has exactly three values`() {
        // Given
        val themes = Theme.values()

        // Then
        assertEquals("Should have exactly 3 themes", 3, themes.size)
        assertTrue("Should contain PELICAN", themes.contains(Theme.PELICAN))
        assertTrue("Should contain FOX", themes.contains(Theme.FOX))
        assertTrue("Should contain ELEPHANT", themes.contains(Theme.ELEPHANT))
    }

    @Test
    fun `BirthdayData supports all valid themes from assignment`() {
        // Given - the three themes from the assignment
        val validThemes = listOf("pelican", "fox", "elephant")
        val testName = "Test Baby"
        val testDob = System.currentTimeMillis()

        // When - creating BirthdayData with each theme
        validThemes.forEach { themeString ->
            val birthdayData = BirthdayData(
                name = testName,
                dob = testDob,
                theme = themeString
            )
            val theme = Theme.fromString(themeString)

            // Then
            assertEquals("Name should match", testName, birthdayData.name)
            assertEquals("DOB should match", testDob, birthdayData.dob)
            assertEquals("Theme string should match", themeString, birthdayData.theme)
            assertNotNull("Theme enum should be valid", theme)
        }
    }

    @Test
    fun `BirthdayData handles long names correctly`() {
        // Given
        val longName = "Baby With A Very Very Long Name That Might Need Wrapping"
        val dob = 1685826000000L
        val theme = "pelican"

        // When
        val birthdayData = BirthdayData(name = longName, dob = dob, theme = theme)

        // Then
        assertEquals("Long name should be preserved", longName, birthdayData.name)
        assertTrue("Name should be longer than typical display width",
            birthdayData.name.length > 20)
    }

    @Test
    fun `BirthdayData handles edge case timestamps`() {
        // Given
        val veryOldTimestamp = 0L // January 1, 1970
        val recentTimestamp = System.currentTimeMillis()
        val futureTimestamp = System.currentTimeMillis() + 1000000L

        // When
        val oldData = BirthdayData("Old Baby", veryOldTimestamp, "fox")
        val recentData = BirthdayData("Recent Baby", recentTimestamp, "elephant")
        val futureData = BirthdayData("Future Baby", futureTimestamp, "pelican")

        // Then
        assertEquals(veryOldTimestamp, oldData.dob)
        assertEquals(recentTimestamp, recentData.dob)
        assertEquals(futureTimestamp, futureData.dob)

        // All should have valid theme enums
        assertNotNull(Theme.fromString(oldData.theme))
        assertNotNull(Theme.fromString(recentData.theme))
        assertNotNull(Theme.fromString(futureData.theme))
    }
}