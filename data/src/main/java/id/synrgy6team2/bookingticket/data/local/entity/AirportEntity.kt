package id.synrgy6team2.bookingticket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_airport")
data class AirportEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Int? = null,

    @ColumnInfo("airportName")
    val airportName: String? = null,

    @ColumnInfo("cityName")
    val cityName: String? = null,

    @ColumnInfo("code")
    val code: String? = null
)
