package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.local.entity.NotificationEntity
import id.synrgy6team2.bookingticket.data.remote.model.NotificationResponse
import id.synrgy6team2.bookingticket.domain.model.NotificationResponseModel

fun NotificationResponse.Data?.toEntityList(token: String): List<NotificationEntity> {
    return this?.notification?.map { it.toEntity(token) } ?: emptyList()
}

fun NotificationResponse.Data.NotificationItem.toEntity(token: String): NotificationEntity {
    return NotificationEntity(
        imageUrl = this.imageUrl,
        createdDatetime = this.createdDatetime,
        id = this.id,
        token = token,
        title = this.title,
        content = this.content
    )
}

fun List<NotificationEntity>?.toDomain(): NotificationResponseModel.Data {
    return NotificationResponseModel.Data(
        notification = this?.map { it.toNotificationItemModel() } ?: emptyList()
    )
}

fun NotificationEntity.toNotificationItemModel(): NotificationResponseModel.Data.NotificationItem {
    return NotificationResponseModel.Data.NotificationItem(
        imageUrl = this.imageUrl,
        createdDatetime = this.createdDatetime,
        id = this.id,
        title = this.title,
        content = this.content
    )
}
