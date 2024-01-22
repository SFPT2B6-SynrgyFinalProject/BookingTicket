package id.synrgy6team2.bookingticket.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_account")
data class ProfileEntity(
    @ColumnInfo("email")
    val email: String? = null,

    @ColumnInfo("fullName")
    val fullName: String? = null,

    @ColumnInfo("gender")
    val gender: String? = null,

    @ColumnInfo("birthDate")
    val birthDate: String? = null,

    @ColumnInfo("noHp")
    val noHp: String? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("token")
    val token: String
)
