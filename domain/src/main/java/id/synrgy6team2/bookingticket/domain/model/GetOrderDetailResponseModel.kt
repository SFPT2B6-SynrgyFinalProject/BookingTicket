package id.synrgy6team2.bookingticket.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetOrderDetailResponseModel(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
): Parcelable {
	@Parcelize
	data class Data(
		val orderId: String? = null,
		val flightDetails: FlightDetails? = null,
		val luggage: Int? = null,
		val orderer: Orderer? = null,
		val priceDetails: PriceDetails? = null,
		val passengerDetails: PassengerDetails? = null,
		val paymentStatus: String? = null,
		val flightClass: String? = null,
		val paymentTime: String? = null
	): Parcelable {
		@Parcelize
		data class PriceDetails(
			val total: Int? = null,
			val tax: Int? = null,
			val basePriceBreakdown: BasePriceBreakdown? = null,
			val totalDicount: Int? = null
		): Parcelable {
			@Parcelize
			data class BasePriceBreakdown(
				val adult: Adult? = null,
				val infant: Infant? = null,
				val child: Child? = null
			): Parcelable {
				@Parcelize
				data class Infant(
					val passengerCount: Int? = null,
					val price: Int? = null
				):Parcelable

				@Parcelize
				data class Child(
					val passengerCount: Int? = null,
					val price: Int? = null
				): Parcelable

				@Parcelize
				data class Adult(
					val passengerCount: Int? = null,
					val price: Int? = null
				): Parcelable
			}
		}

		@Parcelize
		data class FlightDetails(
			val flightCode: String? = null,
			val arrival: Arrival? = null,
			val departure: Departure? = null,
			val airline: Airline? = null
		): Parcelable {
			@Parcelize
			data class Arrival(
				val dateTime: String? = null,
				val airportName: String? = null,
				val city: String? = null,
				val airportId: Int? = null,
				val code: String? = null
			): Parcelable

			@Parcelize
			data class Departure(
				val dateTime: String? = null,
				val airportName: String? = null,
				val city: String? = null,
				val airportId: Int? = null,
				val code: String? = null
			): Parcelable

			@Parcelize
			data class Airline(
				val name: String? = null,
				val iconUrl: String? = null,
				val airlineId: Int? = null
			): Parcelable
		}

		@Parcelize
		data class PassengerDetails(
			val adult: List<String?>? = null,
			val infant: List<String?>? = null,
			val child: List<String?>? = null,
			val passengerTotal: Int? = null
		): Parcelable

		@Parcelize
		data class Orderer(
			val phoneNumber: String? = null,
			val fullName: String? = null,
			val email: String? = null
		): Parcelable
	}
}
