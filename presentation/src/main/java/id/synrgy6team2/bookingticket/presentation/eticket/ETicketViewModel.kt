package id.synrgy6team2.bookingticket.presentation.eticket

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import javax.inject.Inject

@HiltViewModel
class ETicketViewModel @Inject constructor() : ViewModel() {
    fun passengerDetail(
        name: String?,
        passenger: GetOrderDetailResponseModel.Data.PassengerDetails?
    ): List<Passenger> {
        val list = mutableListOf<Passenger>()
        list.add(Passenger(name, "Dewasa"))
        passenger?.adult?.let {
            list.addAll(it.map { name -> Passenger(name, "Dewasa") })
        }
        passenger?.child?.let {
            list.addAll(it.map { name -> Passenger(name, "Anak Kecil") })
        }
        passenger?.infant?.let {
            list.addAll(it.map { name -> Passenger(name, "Bayi") })
        }
        return list.distinct()
    }

    data class Passenger(val name: String?, val type: String)
}
