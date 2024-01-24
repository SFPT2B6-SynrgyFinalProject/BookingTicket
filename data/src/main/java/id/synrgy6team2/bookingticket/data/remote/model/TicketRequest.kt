package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class TicketRequest(
    @SerializedName("classId") val classId: Int? = null,
    @SerializedName("dataPerPage") val dataPerPage: Int? = null,
    @SerializedName("passenger") val passenger: Passenger? = null,
    @SerializedName("departureCode") val departureCode: String? = null,
    @SerializedName("sortBy") val sortBy: List<String?>? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("departureDateStart") val departureDateStart: String? = null,
    @SerializedName("arrivalCode") val arrivalCode: String? = null,
    @SerializedName("departureDateEnd") val departureDateEnd: String? = null,
    @SerializedName("airlineId") val airlineId: List<Long?>? = null
) {
    data class Passenger(
        @SerializedName("adult") val adult: Int? = null,
        @SerializedName("infant") val infant: Int? = null,
        @SerializedName("child") val child: Int? = null
    )
}
