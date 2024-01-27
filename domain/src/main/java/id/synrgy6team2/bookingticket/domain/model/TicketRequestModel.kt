package id.synrgy6team2.bookingticket.domain.model

data class TicketRequestModel(
	val classId: Int? = null,
	val dataPerPage: Int? = null,
	val passenger: Passenger? = null,
	val departureCode: String? = null,
	val sortBy: List<String?>? = null,
	val page: Int? = null,
	val departureDateStart: String? = null,
	val arrivalCode: String? = null,
	val departureDateEnd: String? = null,
	val airlineId: List<Long?>? = null
) {
	data class Passenger(
		val adult: Int? = null,
		val infant: Int? = null,
		val child: Int? = null
	)
}
