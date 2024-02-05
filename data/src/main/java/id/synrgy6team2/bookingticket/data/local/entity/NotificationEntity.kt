package id.synrgy6team2.bookingticket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_notification")
data class NotificationEntity(
    @ColumnInfo("imageUrl")
    val imageUrl: String? = null,

    @ColumnInfo("createdDatetime")
    val createdDatetime: String? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Int? = null,

    @ColumnInfo("token")
    val token: String,

    @ColumnInfo("title")
    val title: String? = null,

    @ColumnInfo("content")
    val content: String? = null
)
