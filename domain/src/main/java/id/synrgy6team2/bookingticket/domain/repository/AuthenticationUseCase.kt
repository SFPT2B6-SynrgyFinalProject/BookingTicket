package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.common.State
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
     * YoutConfigurationViewModel { output ->
     *      when(output) { uiState ->
     *          State.Loading -> YourConfigurationUI
     *          State.Success -> YoutConfigurationUI(uiState.data)
     *          State.Error -> YoutConfigurationUI(uiState.message)
     *      }
     * }
     * ```
     *
     * @param field [LoginRequestModel] - Data Class (LoginRequestModel)
     *
     * @return [LiveData] - LiveData -> State ->
     * LOADING, SUCCESS (LoginResponseModel), ERROR (String)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeLogin(field: LoginRequestModel): LiveData<State<LoginResponseModel>> {
        return authenticationRepository.login(field)
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
     * YoutConfigurationViewModel { output ->
     *      when(output) { uiState ->
     *          State.Loading -> YourConfigurationUI
     *          State.Success -> YoutConfigurationUI(uiState.data)
     *          State.Error -> YoutConfigurationUI(uiState.message)
     *      }
     * }
     * ```
     *
     * @param field [RegisterRequestModel] - Data Class (RegisterResponseModel)
     *
     * @return [RegisterResponseModel] - LiveData -> State ->
     * LOADING, SUCCESS (RegisterResponseModel), ERROR (String)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeRegister(field: RegisterRequestModel): LiveData<State<RegisterResponseModel>> {
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
     * YoutConfigurationViewModel { output ->
     *      when(output) { uiState ->
     *          State.Loading -> YourConfigurationUI
     *          State.Success -> YoutConfigurationUI(uiState.data)
     *          State.Error -> YoutConfigurationUI(uiState.message)
     *      }
     * }
     * ```
     *
     * @param field [ForgotPasswordRequestModel] - Data Class (ForgotPasswordResponseModel)
     *
     * @return [ForgotPasswordResponseModel] - LiveData -> State ->
     * LOADING, SUCCESS (ForgotPasswordResponseModel), ERROR (String)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeForgotPassword(field: ForgotPasswordRequestModel): LiveData<State<ForgotPasswordResponseModel>> {
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
     * YoutConfigurationViewModel { output ->
     *      when(output) { uiState ->
     *          State.Loading -> YourConfigurationUI
     *          State.Success -> YoutConfigurationUI(uiState.data)
     *          State.Error -> YoutConfigurationUI(uiState.message)
     *      }
     * }
     * ```
     *
     * @param field [ResetPasswordRequestModel] - Data Class (ResetPasswordRequestModel)
     *
     * @return [ResetPasswordResponseModel] - LiveData -> State ->
     * LOADING, SUCCESS (ResetPasswordResponseModel), ERROR (String)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun executeResetPassword(field: ResetPasswordRequestModel): LiveData<State<ResetPasswordResponseModel>> {
        return authenticationRepository.resetPassword(field)
    }
}