package id.synrgy6team2.bookingticket.domain.model

data class TicketResponseModel(
	val data: Data? = null,
	val status: String? = null
) {
	data class Data(
		val availableFlight: List<AvailableFlightItem?>? = null
	) {
		data class AvailableFlightItem(
			val arrivalAirportCode: String? = null,
			val withFood: Boolean? = null,
			val discountedPricePerPerson: Int? = null,
			val durationInMin: Int? = null,
			val luggage: Int? = null,
			val withLuggage: Boolean? = null,
			val departureDateTime: String? = null,
			val arrivalDateTime: String? = null,
			val airline: Airline? = null,
			val ticketId: Int? = null,
			val departureAirportCode: String? = null,
			val basePricePerPerson: Int? = null
		) {
			data class Airline(
				val name: String? = null,
				val iconUrl: String? = null,
				val airlineId: Int? = null
			)
		}
	}
}