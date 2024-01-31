package id.synrgy6team2.bookingticket.domain.model

data class GetOrderDetailResponseModel(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
) {
	data class Data(
		val orderId: String? = null,
		val flightDetails: FlightDetails? = null,
		val luggage: Int? = null,
		val orderer: Orderer? = null,
		val priceDetails: PriceDetails? = null,
		val passengerDetails: PassengerDetails? = null,
		val paymentStatus: String? = null
	) {
		data class PriceDetails(
			val total: Int? = null,
			val tax: Int? = null,
			val basePriceBreakdown: BasePriceBreakdown? = null,
			val totalDicount: Int? = null
		) {
			data class BasePriceBreakdown(
				val adult: Adult? = null,
				val infant: Infant? = null,
				val child: Child? = null
			) {
				data class Infant(
					val passengerCount: Int? = null,
					val price: Int? = null
				)

				data class Child(
					val passengerCount: Int? = null,
					val price: Int? = null
				)

				data class Adult(
					val passengerCount: Int? = null,
					val price: Int? = null
				)
			}
		}

		data class FlightDetails(
			val flightCode: String? = null,
			val arrival: Arrival? = null,
			val departure: Departure? = null,
			val airline: Airline? = null
		) {
			data class Arrival(
				val dateTime: String? = null,
				val airportName: String? = null,
				val city: String? = null,
				val airportId: Int? = null
			)

			data class Departure(
				val dateTime: String? = null,
				val airportName: String? = null,
				val city: String? = null,
				val airportId: Int? = null
			)

			data class Airline(
				val name: String? = null,
				val iconUrl: String? = null,
				val airlineId: Int? = null
			)
		}

		data class PassengerDetails(
			val adult: List<String?>? = null,
			val infant: List<Any?>? = null,
			val child: List<Any?>? = null
		)

		data class Orderer(
			val phoneNumber: String? = null,
			val fullName: String? = null,
			val email: String? = null
		)
	}
}
