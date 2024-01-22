package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import id.synrgy6team2.bookingticket.data.remote.model.ProfileResponse
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel

fun ProfileResponse.ProfileResultResponse.toLocal(token: String): ProfileEntity {
    return ProfileEntity(email, fullName, gender, birthDate, noHp, token)
}

fun ProfileEntity.toProfileDomain(): ProfileResponseModel.ProfileResultResponseModel {
    return ProfileResponseModel.ProfileResultResponseModel(email, fullName, gender, birthDate, noHp)
}