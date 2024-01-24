package id.synrgy6team2.bookingticket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_flight_type")
data class FlightClassEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Int? = null,

    @ColumnInfo("name")
    val name: String? = null
)
