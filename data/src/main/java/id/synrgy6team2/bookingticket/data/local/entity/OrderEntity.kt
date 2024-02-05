package id.synrgy6team2.bookingticket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_order")
data class OrderEntity(
    @ColumnInfo("totalPassengers")
    val totalPassengers: Int? = null,

    @ColumnInfo("flightCode")
    val flightCode: String? = null,

    @ColumnInfo("arrival")
    val arrival: Arrival? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("orderId")
    val orderId: String,

    @ColumnInfo("departure")
    val departure: Departure? = null,

    @ColumnInfo("airline")
    val airline: Airline? = null,

    @ColumnInfo("paymentStatus")
    val paymentStatus: String? = null,

    @ColumnInfo("token")
    val token: String,

    @ColumnInfo("status")
    val status: String? = null
) {
    data class Departure(
        val dateTime: String? = null,
        val airportName: String? = null,
        val city: String? = null,
        val airportId: Int? = null,
        val code: String? = null
    )

    data class Airline(
        val name: String? = null,
        val iconUrl: String? = null,
        val airlineId: Int? = null
    )

    data class Arrival(
        val dateTime: String? = null,
        val airportName: String? = null,
        val city: String? = null,
        val airportId: Int? = null,
        val code: String? = null
    )
}
