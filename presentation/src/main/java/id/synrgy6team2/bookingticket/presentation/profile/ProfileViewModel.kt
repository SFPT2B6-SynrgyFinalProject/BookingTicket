package id.synrgy6team2.bookingticket.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.R
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _profileSettings: MutableLiveData<List<ProfileModel>> = MutableLiveData(listOf(
        ProfileModel(R.drawable.ic_notification, R.string.txt_notification, "notification"),
        ProfileModel(R.drawable.ic_account_customization, R.string.txt_account_customization, "customization_account"),
        ProfileModel(R.drawable.ic_payment, R.string.txt_payment, "payment"),
        ProfileModel(R.drawable.ic_my_coupon, R.string.txt_coupon, "coupon"),
        ProfileModel(R.drawable.ic_support_center, R.string.txt_support_center, "support_center"),
        ProfileModel(R.drawable.ic_settings, R.string.txt_settings, "settings")
    ))

    val profileSettings: LiveData<List<ProfileModel>> = _profileSettings
}