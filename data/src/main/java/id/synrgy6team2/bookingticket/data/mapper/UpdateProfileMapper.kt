package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserRequest
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserResponse
import id.synrgy6team2.bookingticket.domain.model.UpdateUserRequestModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserResponseModel

fun UpdateUserRequestModel.toData(): UpdateUserRequest {
    return UpdateUserRequest(email, fullName, noHp, birthDate, gender)
}

fun UpdateUserResponse.UpdateUserResultResponse.toLocal(token: String): ProfileEntity {
    return ProfileEntity(email, fullName, gender, birthDate, noHp, token)
}

fun ProfileEntity.toUpdateProfileDomain(): UpdateUserResponseModel.UpdateUserResultResponseModel {
    return UpdateUserResponseModel.UpdateUserResultResponseModel(email, fullName, noHp, birthDate, gender)
}