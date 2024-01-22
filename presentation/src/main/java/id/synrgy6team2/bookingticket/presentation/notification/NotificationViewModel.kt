package id.synrgy6team2.bookingticket.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.R
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    private val _notification: MutableLiveData<List<NotificationModel>> = MutableLiveData(listOf(
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?"),
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?"),
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?"),
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?"),
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?"),
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?"),
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?"),
        NotificationModel(R.drawable.ic_my_account, "Liburan akhir pekan mau kemana aja nih?")
    ))

    val notification: LiveData<List<NotificationModel>> = _notification
}