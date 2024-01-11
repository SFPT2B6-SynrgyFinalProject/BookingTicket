package id.synrgy6team2.bookingticket.domain.repository

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
     * Example Usage:
     * ```
     * [INPUT] :
     * val field = LoginRequestModel(
     *      email = "yourname@email.com",
     *      password = "YourSecretPassword123"
     * )
     * executeLogin(field)
     *
     * [OUTPUT] :
     * val response = executeLogin(field)
     * YourViewModel(response)
     * ```
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
     * Example Usage:
     * ```
     * [INPUT] :
     * val field = LoginRequestModel(
     *      googleToken = "googleIdToken"
     * )
     * executeLogin(field)
     *
     * [OUTPUT] :
     * val response = executeLogin(field)
     * YourViewModel(response)
     * ```
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
     * Example Usage:
     * ```
     * [INPUT] :
     * val field = RegisterRequestModel(
     *      email = "yourname@email.com",
     *      password = "YourSecretPassword123",
     *      fullName = "Your Full Name",
     *      birthDate = "1996-10-15T00:00:00.000Z"
     *      gender = "String"
     * )
     * executeRegister(field)
     *
     * [OUTPUT] :
     * val response = executeRegister(field)
     * YourViewModel(response)
     * ```
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
     * FORGOT PASSWORD Feature
     *
     * Example Usage:
     * ```
     * [INPUT] :
     * val field = ForgotPasswordRequestModel(
     *      email = "yourname@email.com"
     * )
     * executeForgotPassword(field)
     *
     * [OUTPUT] :
     * val response = executeForgotPassword(field)
     * YoutViewModel(response)
     * ```
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
     * Example Usage:
     * ```
     * [INPUT] :
     * val field = ResetPasswordRequestModel(
     *      newPassword = "YourSecretPassword123",
     *      confirmPassword = "YourSecretPassword123"
     * )
     * executeResetPassword(field)
     *
     * [OUTPUT] :
     * val response = executeResetPassword(field)
     * YourViewModel(response)
     * ```
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
     *
     * Example Usage:
     * ```
     * [INPUT] :
     * executeLogout()
     *
     * [OUTPUT] :
     * Nothing!
     * ```
     * */
    suspend fun executeLogout() = authenticationRepository.logout()

    /**
     * LOGOUT Feature
     *
     * Example Usage:
     * ```
     * [INPUT] :
     * executeCheckLogged()
     *
     * [OUTPUT] :
     * val response = executeCheckLogged()
     * YourViewModel(response)
     * ```
     *
     * @return [Boolean] - Boolean (True) or (False)
     * */
    suspend fun executeCheckLogged(): Boolean {
        return authenticationRepository.checkLogged()
    }
}