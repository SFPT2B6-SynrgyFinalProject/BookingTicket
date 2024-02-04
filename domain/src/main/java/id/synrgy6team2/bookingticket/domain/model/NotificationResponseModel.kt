package id.synrgy6team2.bookingticket.domain.model

data class NotificationResponseModel(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
) {
	data class Data(
		val notification: List<NotificationItem>? = null
	) {
		data class NotificationItem(
			val imageUrl: String? = null,
			val createdDatetime: String? = null,
			val id: Int? = null,
			val title: String? = null,
			val content: String? = null
		)
	}
}

