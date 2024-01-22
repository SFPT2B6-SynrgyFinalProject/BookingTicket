package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordResponseModel
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserRequestModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserResponseModel

interface AccountRepository {
    fun profile(): LiveData<ProfileResponseModel>
    fun updateProfile(field: UpdateUserRequestModel): LiveData<UpdateUserResponseModel>
    suspend fun changePassword(field: ChangePasswordRequestModel): ChangePasswordResponseModel
}