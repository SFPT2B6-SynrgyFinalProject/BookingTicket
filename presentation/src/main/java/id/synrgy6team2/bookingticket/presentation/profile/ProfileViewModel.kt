package id.synrgy6team2.bookingticket.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AccountUseCase
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    accountUseCase: AccountUseCase,
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    private val _profileSetup: MutableList<ProfileModel> = mutableListOf(
        ProfileModel(R.drawable.ic_notification, R.string.txt_notification, "notification"),
        ProfileModel(R.drawable.ic_account_customization, R.string.txt_account_customization, "customization_account"),
        ProfileModel(R.drawable.ic_payment, R.string.txt_payment, "payment"),
        ProfileModel(R.drawable.ic_my_coupon, R.string.txt_coupon, "coupon"),
        ProfileModel(R.drawable.ic_support_center, R.string.txt_support_center, "support_center"),
        ProfileModel(R.drawable.ic_settings, R.string.txt_settings, "settings"),
        ProfileModel(R.drawable.ic_logout, R.string.txt_logout, "logout")
    )
    private val _profileSettings: MutableLiveData<List<ProfileModel>> = MutableLiveData()

    val profileSettings: LiveData<List<ProfileModel>> = _profileSettings
    val profile: LiveData<ProfileResponseModel> = accountUseCase.executeProfile()
    val logged: LiveData<Boolean> = authenticationUseCase.executeCheckLogged().map {
        if (!it) {
            _profileSettings.postValue(_profileSetup.dropLast(1))
            false
        } else {
            _profileSettings.postValue(_profileSetup)
            true
        }
    }

    fun logout(value: GoogleSignInClient) {
        viewModelScope.launch {
            try {
                authenticationUseCase.executeLogout()
                value.signOut().await()
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }
}