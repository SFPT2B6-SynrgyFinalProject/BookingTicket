package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterResponseModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordResponseModel

/**
 * Application UseCase for Authentication feature that will connect to REMOTE & LOCAL
 * to interact directly to UI Layer / Presentation Layer
 * */
class AuthenticationUseCase(private val authenticationRepository: AuthenticationRepository) {
    /**
     * LOGIN Features
     *
     * @param field [LoginRequestModel] - Data Class (LoginRequestModel)
     *
     * @return [LoginResponseModel] - Data Class (LoginResponseModel)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeLogin(field: LoginRequestModel): LoginResponseModel {
        return authenticationRepository.login(field)
    }

    /**
     * LOGIN Features
     *
     * @param field [LoginRequestModel] - Data Class (LoginRequestModel)
     *
     * @return [LoginResponseModel] - Data Class (LoginResponseModel)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeGoogle(field: LoginRequestModel): LoginResponseModel {
        return authenticationRepository.google(field)
    }

    /**
     * REGISTER Feature
     *
     * @param field [RegisterRequestModel] - Data Class (RegisterRequestModel)
     *
     * @return [RegisterResponseModel] - Data Class (RegisterResponseModel)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeRegister(field: RegisterRequestModel): RegisterResponseModel {
        return authenticationRepository.register(field)
    }

    /**
     * VERIFY Feature
     *
     * @param token [Integer] - Data Primitive (Integer)
     *
     * @return [Unit] - Nothing! (Unit)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeVerify(token: Int?): Unit {
        return authenticationRepository.verify(token)
    }

    /**
     * FORGOT PASSWORD Feature
     *
     * @param field [ForgotPasswordRequestModel] - Data Class (ForgotPasswordRequestModel)
     *
     * @return [ForgotPasswordResponseModel] - Data Class (ForgotPasswordResponseModel)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeForgotPassword(field: ForgotPasswordRequestModel): ForgotPasswordResponseModel {
        return authenticationRepository.forgotPassword(field)
    }

    /**
     * RESET PASSWORD Feature
     *
     * @param field [ResetPasswordRequestModel] - Data Class (ResetPasswordRequestModel)
     *
     * @return [ResetPasswordResponseModel] - Data Class (ResetPasswordResponseModel)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeResetPassword(field: ResetPasswordRequestModel): ResetPasswordResponseModel {
        return authenticationRepository.resetPassword(field)
    }

    /**
     * LOGOUT Feature
     * */
    suspend fun executeLogout() {
        return authenticationRepository.logout()
    }

    /**
     * LOGOUT Feature
     *
     * @return [Boolean] - Boolean (True) or (False)
     * */
    fun executeCheckLogged(): LiveData<Boolean> {
        return authenticationRepository.checkLogged()
    }
}