package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.RegisterRequest
import id.synrgy6team2.bookingticket.data.remote.model.RegisterResponse
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterResponseModel

fun RegisterRequestModel.toData(): RegisterRequest {
    return RegisterRequest(email, password, fullName, birthDate, gender)
}

fun RegisterResponse.toDomain(): RegisterResponseModel {
    return RegisterResponseModel(status, data?.toDomain())
}

fun RegisterResponse.RegisterResultResponse.toDomain(): RegisterResponseModel.RegisterResultResponse {
    return RegisterResponseModel.RegisterResultResponse(email, fullName, birthDate, gender)
}