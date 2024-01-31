package id.synrgy6team2.bookingticket.presentation.changeprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserRequestModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AccountUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeProfileViewModel @Inject constructor(
    private val useCase: AccountUseCase
) : ViewModel() {
    private var _changeProfile: LiveEvent<StateLocal<UpdateUserResponseModel>> = LiveEvent()

    val changeProfile: LiveData<StateLocal<UpdateUserResponseModel>> = _changeProfile
    val showProfile: LiveData<ProfileResponseModel> = useCase.executeProfile()
    val saveStateBirthDate: MutableLiveData<String?> = MutableLiveData()

    fun changeProfile(value: UpdateUserRequestModel) {
        viewModelScope.launch {
            useCase.executeUpdateProfile(value).asFlow().collectLatest {
                _changeProfile.postValue(it)
            }
        }
    }
}