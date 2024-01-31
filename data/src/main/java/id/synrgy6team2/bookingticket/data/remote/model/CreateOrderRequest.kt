package id.synrgy6team2.bookingticket.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(

	@field:SerializedName("classId")
	val classId: Int? = null,

	@field:SerializedName("orderer")
	val orderer: Orderer? = null,

	@field:SerializedName("passengerDetails")
	val passengerDetails: PassengerDetails? = null,

	@field:SerializedName("ticketId")
	val ticketId: Int? = null
) {
	data class Orderer(

		@field:SerializedName("phoneNumber")
		val phoneNumber: String? = null,

		@field:SerializedName("fullName")
		val fullName: String? = null,

		@field:SerializedName("email")
		val email: String? = null
	)

	data class PassengerDetails(

		@field:SerializedName("adult")
		val adult: List<String?>? = listOf(),

		@field:SerializedName("infant")
		val infant: List<String?>? = listOf(),

		@field:SerializedName("child")
		val child: List<String?>? = listOf()
	)
}


