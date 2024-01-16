package id.synrgy6team2.bookingticket.common

import java.time.Instant

fun Long.isMoreThanTenMinutes(): Boolean {
    val currentTimestamp = Instant.now().epochSecond
    val tenMinutesAgo = currentTimestamp - 600
    return this < tenMinutesAgo
}

fun timeStamp(): Long {
    return Instant.now().epochSecond
}