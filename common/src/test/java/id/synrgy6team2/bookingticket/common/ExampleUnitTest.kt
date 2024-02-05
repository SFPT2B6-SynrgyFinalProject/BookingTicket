package id.synrgy6team2.bookingticket.common

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun convertToUtcFullDateTime_isCorrect() {
        // Test case 1: Input valid
        val input1 = "2612"
        val expected1 = "2026-12-01T00:00:00.000Z"
        val result1 = input1.convertToUtcFullDateTime()
        assertEquals(expected1, result1) // Pastikan panjang hasil sesuai dengan format

        // Test case 2: Input tidak valid (tidak empat digit)
        val input2 = "123"
        val expected2 = "" // Input tidak valid, seharusnya kembali ke input asli
        val result2 = input2.convertToUtcFullDateTime()
        assertEquals(expected2, result2)

        // Test case 3: Input kosong
        val input3 = "1226"
        val expected3 = "" // Input kosong, seharusnya kembali ke input asli
        val result3 = input3?.convertToUtcFullDateTime()
        assertEquals(expected3, result3)
    }
}