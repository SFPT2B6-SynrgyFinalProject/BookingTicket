package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class TicketResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
	data class Data(

		@field:SerializedName("availableFlight")
		val availableFlight: List<AvailableFlightItem?>? = null
	) {

		data class AvailableFlightItem(

			@field:SerializedName("arrivalAirportCode")
			val arrivalAirportCode: String? = null,

			@field:SerializedName("withFood")
			val withFood: Boolean? = null,

			@field:SerializedName("discountedPricePerPerson")
			val discountedPricePerPerson: Int? = null,

			@field:SerializedName("durationInMin")
			val durationInMin: Int? = null,

			@field:SerializedName("luggage")
			val luggage: Int? = null,

			@field:SerializedName("withLuggage")
			val withLuggage: Boolean? = null,

			@field:SerializedName("departureDateTime")
			val departureDateTime: String? = null,

			@field:SerializedName("arrivalDateTime")
			val arrivalDateTime: String? = null,

			@field:SerializedName("airline")
			val airline: Airline? = null,

			@field:SerializedName("ticketId")
			val ticketId: Int? = null,

			@field:SerializedName("departureAirportCode")
			val departureAirportCode: String? = null,

			@field:SerializedName("basePricePerPerson")
			val basePricePerPerson: Int? = null
		) {
			data class Airline(

				@field:SerializedName("name")
				val name: String? = null,

				@field:SerializedName("iconUrl")
				val iconUrl: String? = null,

				@field:SerializedName("airlineId")
				val airlineId: Int? = null
			)
		}
	}
}