package id.synrgy6team2.bookingticket.domain.model

data class GetOrderResponseModel(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
) {
	data class Data(
		val orders: List<OrdersItem>? = null
	) {
		data class OrdersItem(
			val totalPassengers: Int? = null,
			val flightCode: String? = null,
			val arrival: Arrival? = null,
			val orderId: String? = null,
			val departure: Departure? = null,
			val airline: Airline? = null,
			val paymentStatus: String? = null,
		) {
			data class Departure(
				val dateTime: String? = null,
				val airportName: String? = null,
				val city: String? = null,
				val airportId: Int? = null,
				val code: String? = null
			)

			data class Airline(
				val name: String? = null,
				val iconUrl: String? = null,
				val airlineId: Int? = null
			)

			data class Arrival(
				val dateTime: String? = null,
				val airportName: String? = null,
				val city: String? = null,
				val airportId: Int? = null,
				val code: String? = null
			)
		}
	}
}
