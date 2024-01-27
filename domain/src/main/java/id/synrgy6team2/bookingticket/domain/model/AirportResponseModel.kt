package id.synrgy6team2.bookingticket.domain.model

data class AirportResponseModel(
	val data: List<DataItem>? = null,
	val status: String? = null
) {
	data class DataItem(
		val airportName: String? = null,
		val code: String? = null,
		val cityName: String? = null,
		val id: Int? = null
	)
}

