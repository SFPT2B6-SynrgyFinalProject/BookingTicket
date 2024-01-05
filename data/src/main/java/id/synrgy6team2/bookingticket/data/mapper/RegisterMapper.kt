package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.RegisterRequest
import id.synrgy6team2.bookingticket.data.remote.model.RegisterResponse
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterResponseModel

fun RegisterRequestModel.toData(): RegisterRequest {
    return RegisterRequest(
        this.email,
        this.noHp,
        this.password,
        this.fullName,
        this.birthDate,
        this.gender
    )
}

fun RegisterResponse.toDomain(): RegisterResponseModel {
    return RegisterResponseModel(
        this.status,
        this.data?.toDomain()
    )
}

fun RegisterResponse.RegisterResultResponse.toDomain(): RegisterResponseModel.RegisterResultResponse {
    return RegisterResponseModel.RegisterResultResponse(
        this.email,
        this.noHp,
        this.password,
        this.fullName,
        this.birthDate,
        this.gender
    )
}