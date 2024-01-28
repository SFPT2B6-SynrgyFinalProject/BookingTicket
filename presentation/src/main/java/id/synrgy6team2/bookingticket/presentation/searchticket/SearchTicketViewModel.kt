package id.synrgy6team2.bookingticket.presentation.searchticket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchTicketViewModel @Inject constructor() : ViewModel() {
    val txtAirportFrom: MutableLiveData<String> = MutableLiveData("")
    val txtAirportTo: MutableLiveData<String> = MutableLiveData("")
    val txtTypeFlight: MutableLiveData<Int> = MutableLiveData(-1)
    val txtDatePickArrival: MutableLiveData<String> = MutableLiveData("")
    val txtDatePickDeparture: MutableLiveData<String> = MutableLiveData("")
}