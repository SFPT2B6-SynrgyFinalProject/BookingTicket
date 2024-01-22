package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordResponse
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordResponseModel

fun ChangePasswordRequestModel.toData(): ChangePasswordRequest {
    return ChangePasswordRequest(currentPassword, newPassword, confirmPassword)
}

fun ChangePasswordResponse.toDomain(): ChangePasswordResponseModel {
    return ChangePasswordResponseModel(status)
}