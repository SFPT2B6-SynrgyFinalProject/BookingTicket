package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetOrderResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
	data class Data(

		@field:SerializedName("orders")
		val orders: List<OrdersItem?>? = null
	) {
		data class OrdersItem(

			@field:SerializedName("totalPassengers")
			val totalPassengers: Int? = null,

			@field:SerializedName("flightCode")
			val flightCode: String? = null,

			@field:SerializedName("arrival")
			val arrival: Arrival? = null,

			@field:SerializedName("orderId")
			val orderId: String? = null,

			@field:SerializedName("departure")
			val departure: Departure? = null,

			@field:SerializedName("airline")
			val airline: Airline? = null,

			@field:SerializedName("paymentStatus")
			val paymentStatus: String? = null,

			@field:SerializedName("flightClass")
			val flightClass: String? = null
		) {
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
		}
	}
}
