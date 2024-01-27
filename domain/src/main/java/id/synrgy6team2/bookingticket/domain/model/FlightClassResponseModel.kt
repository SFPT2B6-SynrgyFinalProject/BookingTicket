package id.synrgy6team2.bookingticket.domain.model

data class FlightClassResponseModel(
	val data: List<DataItem>? = null,
	val status: String? = null
) {
	data class DataItem(
		val name: String? = null,
		val id: Int? = null
	)

}
