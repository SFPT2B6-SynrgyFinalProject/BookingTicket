package id.synrgy6team2.bookingticket.common

import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.isMoreThanTenMinutes(): Boolean {
    val currentTimestamp = Instant.now().epochSecond
    val tenMinutesAgo = currentTimestamp - 600
    return this < tenMinutesAgo
}

fun timeStamp(): Long {
    return Instant.now().epochSecond
}

fun String.parseToTime(): String {
    val originalZonedDateTime = ZonedDateTime.parse(this)
    val localDateTime = originalZonedDateTime.toLocalDateTime()
    return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun Int.parseToStringTime(): String {
    if (this < 0) {
        return "0m"
    }

    val hours = this / 60
    val remainingMinutes = this % 60

    return when {
        hours > 0 && remainingMinutes > 0 -> "${hours}j${remainingMinutes}m"
        hours > 0 -> "${hours}j"
        remainingMinutes > 0 -> "${remainingMinutes}m"
        else -> "0m"
    }
}

fun Int.parseToRupiah(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(this).replace("IDR","")
}

fun String.truncateString(wordCount: Int): String {
    val words = this.split("\\s+".toRegex())
    val truncatedWords = words.take(wordCount)
    return truncatedWords.joinToString(" ")
}

fun String.toCustomFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM", Locale("id", "ID"))
    val instant = Instant.parse(this)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"))
    return localDateTime.format(formatter)
}

fun String.convertToUtcFullDateTime(): String {
    val inputString = this
    val year = inputString.substring(0, 2)
    val month = inputString.substring(2, 4)
    val localDateTime = LocalDateTime.of(2000 + year.toInt(), month.toInt(), 1, 0, 0)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-31'T'HH:mm:ss.SSS'Z'")
    return localDateTime.format(formatter)
}

fun String?.toImgUrl(): String {
    return "https://be-finpro-ev4x53wgca-uc.a.run.app$this"
}