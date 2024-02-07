package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetOrderDetailResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
	data class Data(

		@field:SerializedName("orderId")
		val orderId: String? = null,

		@field:SerializedName("flightDetails")
		val flightDetails: FlightDetails? = null,

		@field:SerializedName("luggage")
		val luggage: Int? = null,

		@field:SerializedName("orderer")
		val orderer: Orderer? = null,

		@field:SerializedName("priceDetails")
		val priceDetails: PriceDetails? = null,

		@field:SerializedName("passengerDetails")
		val passengerDetails: PassengerDetails? = null,

		@field:SerializedName("paymentStatus")
		val paymentStatus: String? = null,

		@field:SerializedName("flightClass")
		val flightClass: String? = null,

		@field:SerializedName("paymentTime")
		val paymentTime: String? = null
	) {
		data class PriceDetails(

			@field:SerializedName("total")
			val total: Int? = null,

			@field:SerializedName("tax")
			val tax: Int? = null,

			@field:SerializedName("basePriceBreakdown")
			val basePriceBreakdown: BasePriceBreakdown? = null,

			@field:SerializedName("totalDicount")
			val totalDicount: Int? = null
		) {
			data class BasePriceBreakdown(

				@field:SerializedName("adult")
				val adult: Adult? = null,

				@field:SerializedName("infant")
				val infant: Infant? = null,

				@field:SerializedName("child")
				val child: Child? = null
			) {
				data class Infant(

					@field:SerializedName("passengerCount")
					val passengerCount: Int? = null,

					@field:SerializedName("price")
					val price: Int? = null
				)

				data class Child(

					@field:SerializedName("passengerCount")
					val passengerCount: Int? = null,

					@field:SerializedName("price")
					val price: Int? = null
				)

				data class Adult(

					@field:SerializedName("passengerCount")
					val passengerCount: Int? = null,

					@field:SerializedName("price")
					val price: Int? = null
				)
			}
		}

		data class FlightDetails(

			@field:SerializedName("flightCode")
			val flightCode: String? = null,

			@field:SerializedName("arrival")
			val arrival: Arrival? = null,

			@field:SerializedName("departure")
			val departure: Departure? = null,

			@field:SerializedName("airline")
			val airline: Airline? = null
		) {
			data class Arrival(

				@field:SerializedName("dateTime")
				val dateTime: String? = null,

				@field:SerializedName("airportName")
				val airportName: String? = null,

				@field:SerializedName("city")
				val city: String? = null,

				@field:SerializedName("airportId")
				val airportId: Int? = null,

				@field:SerializedName("code")
				val code: String? = null
			)

			data class Departure(

				@field:SerializedName("dateTime")
				val dateTime: String? = null,

				@field:SerializedName("airportName")
				val airportName: String? = null,

				@field:SerializedName("city")
				val city: String? = null,

				@field:SerializedName("airportId")
				val airportId: Int? = null,

				@field:SerializedName("code")
				val code: String? = null
			)

			data class Airline(

				@field:SerializedName("name")
				val name: String? = null,

				@field:SerializedName("iconUrl")
				val iconUrl: String? = null,

				@field:SerializedName("airlineId")
				val airlineId: Int? = null
			)
		}

		data class PassengerDetails(

			@field:SerializedName("adult")
			val adult: List<String?>? = null,

			@field:SerializedName("infant")
			val infant: List<String?>? = null,

			@field:SerializedName("child")
			val child: List<String?>? = null,

			@field:SerializedName("passengerTotal")
			val passengerTotal: Int? = null
		)

		data class Orderer(

			@field:SerializedName("phoneNumber")
			val phoneNumber: String? = null,

			@field:SerializedName("fullName")
			val fullName: String? = null,

			@field:SerializedName("email")
			val email: String? = null
		)
	}
}
