package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordResponseModel
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserRequestModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserResponseModel

/**
 * Application UseCase for Authentication feature that will connect to REMOTE & LOCAL
 * to interact directly to UI Layer / Presentation Layer
 * */
class AccountUseCase(private val accountRepository: AccountRepository) {
    /**
     * PROFILE Feature
     *
     * @return [ProfileResponseModel] - Data Class (ProfileResponseModel)
     * */
    fun executeProfile(): LiveData<ProfileResponseModel> {
        return accountRepository.profile()
    }

    /**
     * UPDATE PROFILE Feature
     *
     * @param field [UpdateUserRequestModel] - Data Class (UpdateUserRequestModel)
     *
     * @return [UpdateUserResponseModel] - Data Class (UpdateUserResponseModel)
     *
     * @exception message [Throwable] - Handle Error
     * */
    fun executeUpdateProfile(field: UpdateUserRequestModel): LiveData<StateLocal<UpdateUserResponseModel>> {
        return accountRepository.updateProfile(field)
    }

    /**
     * CHANGE PASSWORD Feature
     *
     * @param field [ChangePasswordRequestModel] -  Data Class (ChangePasswordRequestModel)
     *
     * @return [ChangePasswordResponseModel] - Data Class (ChangePasswordResponseModel)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeChangePassword(field: ChangePasswordRequestModel): ChangePasswordResponseModel {
        return accountRepository.changePassword(field)
    }
}