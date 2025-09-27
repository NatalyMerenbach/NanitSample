package utils

import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate
import java.time.ZoneId

class AgeCalculatorTest {

    @Test
    fun `calculateAge returns correct months for baby under 1 year`() {
        // Given: A baby born 8 months ago
        val today = LocalDate.now()
        val dobDate = today.minusMonths(8)
        val dobMillis = dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        assertEquals(8, result.value)
        assertEquals("months old", result.unit)
        assertTrue(result.isInMonths)
    }

    @Test
    fun `calculateAge returns correct singular month for 1 month old baby`() {
        // Given: A baby born 1 month ago
        val today = LocalDate.now()
        val dobDate = today.minusMonths(1)
        val dobMillis = dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        assertEquals(1, result.value)
        assertEquals("month old", result.unit)
        assertTrue(result.isInMonths)
    }

    @Test
    fun `calculateAge returns correct years for child over 1 year`() {
        // Given: A child born 2 years ago
        val today = LocalDate.now()
        val dobDate = today.minusYears(2)
        val dobMillis = dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        assertEquals(2, result.value)
        assertEquals("years old", result.unit)
        assertFalse(result.isInMonths)
    }

    @Test
    fun `calculateAge returns correct singular year for 1 year old child`() {
        // Given: A child born exactly 1 year ago
        val today = LocalDate.now()
        val dobDate = today.minusYears(1)
        val dobMillis = dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        assertEquals(1, result.value)
        assertEquals("year old", result.unit)
        assertFalse(result.isInMonths)
    }

    @Test
    fun `calculateAge returns 0 months for newborn baby`() {
        // Given: A baby born today
        val today = LocalDate.now()
        val dobMillis = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        assertEquals(0, result.value)
        assertEquals("months old", result.unit)
        assertTrue(result.isInMonths)
    }

    @Test
    fun `calculateAge handles edge case at exactly 12 months`() {
        // Given: A baby born exactly 12 months ago
        val today = LocalDate.now()
        val dobDate = today.minusMonths(12)
        val dobMillis = dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        // Should be 1 year old, not 12 months
        assertEquals(1, result.value)
        assertEquals("year old", result.unit)
        assertFalse(result.isInMonths)
    }

    @Test
    fun `calculateAge handles edge case at 11 months`() {
        // Given: A baby born 11 months ago
        val today = LocalDate.now()
        val dobDate = today.minusMonths(11)
        val dobMillis = dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        assertEquals(11, result.value)
        assertEquals("months old", result.unit)
        assertTrue(result.isInMonths)
    }

    @Test
    fun `calculateAge works with historical date from assignment example`() {
        // Given: DOB from assignment example (June 3, 2023)
        val dobMillis = 1685826000000L // This is the example from the PDF

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        // Should calculate correct age based on current date
        assertTrue("Age should be positive", result.value >= 0)
        assertNotNull("Unit should not be null", result.unit)
        assertTrue("Unit should contain 'old'", result.unit.contains("old"))
    }

    @Test
    fun `calculateAge handles maximum age limit scenario`() {
        // Given: A child born 9 years ago (assignment limit)
        val today = LocalDate.now()
        val dobDate = today.minusYears(9)
        val dobMillis = dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // When
        val result = AgeCalculator.calculateAge(dobMillis)

        // Then
        assertEquals(9, result.value)
        assertEquals("years old", result.unit)
        assertFalse(result.isInMonths)
    }
}