package id.synrgy6team2.bookingticket.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketRequestModel(
	val classId: Int? = null,
	val passenger: Passenger? = null,
	val departureCode: String? = null,
	val sortBy: List<String?>? = null,
	val departureDateStart: String? = null,
	val arrivalCode: String? = null,
	val departureDateEnd: String? = null,
	val airlineId: List<Long?>? = null
) : Parcelable {
	@Parcelize
	data class Passenger(
		val adult: Int? = null,
		val infant: Int? = null,
		val child: Int? = null
	) : Parcelable
}

