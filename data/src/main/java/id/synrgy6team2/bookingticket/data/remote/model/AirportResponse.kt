package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class AirportResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
) {
    data class DataItem(

        @field:SerializedName("airportName")
        val airportName: String? = null,

        @field:SerializedName("code")
        val code: String? = null,

        @field:SerializedName("cityName")
        val cityName: String? = null,

        @field:SerializedName("id")
        val id: Int? = null
    )
}
