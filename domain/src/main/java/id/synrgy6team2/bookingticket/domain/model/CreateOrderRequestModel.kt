package id.synrgy6team2.bookingticket.domain.model

data class CreateOrderRequestModel(
	val classId: Int? = null,
	val orderer: Orderer? = null,
	val passengerDetails: PassengerDetails? = null,
	val ticketId: Int? = null
) {
	data class Orderer(
		val phoneNumber: String? = null,
		val fullName: String? = null,
		val email: String? = null
	)

	data class PassengerDetails(
		val adult: List<String?>? = listOf(),
		val infant: List<String?>? = listOf(),
		val child: List<String?>? = listOf()
	)
}


