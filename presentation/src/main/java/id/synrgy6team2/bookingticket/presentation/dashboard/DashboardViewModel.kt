package id.synrgy6team2.bookingticket.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.R
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val _mainMenu: MutableLiveData<List<DashboardMainMenuModel>> = MutableLiveData(listOf(
        DashboardMainMenuModel(R.drawable.ic_clarity_plane, "Ticket Pesawat"),
        DashboardMainMenuModel(R.drawable.ic_bxs_offer, "Spesial Offers"),
        DashboardMainMenuModel(R.drawable.ic_package, "Paket Diskon"),
        DashboardMainMenuModel(R.drawable.ic_light_trip, "Panduan Wisata")
    ))
    private val _recomended: MutableLiveData<List<DashboardRecomendedModel>> = MutableLiveData(listOf(
        DashboardRecomendedModel(R.drawable.home_dummy, "Yogyakarta", "Diskon hingga 40%"),
        DashboardRecomendedModel(R.drawable.home_dummy, "Surabaya", "Diskon hingga 40%"),
        DashboardRecomendedModel(R.drawable.home_dummy, "Jakarta", "Diskon hingga 40%"),
        DashboardRecomendedModel(R.drawable.home_dummy, "Bali", "Diskon hingga 40%")
    ))
    private val _history: MutableLiveData<List<DashboardHistoryModel>> = MutableLiveData(listOf(
        DashboardHistoryModel("SUR", "JKT", "JUM 13 Feb - SAB 14 Feb"),
        DashboardHistoryModel("DPS", "JKT", "JUM 13 Feb - SAB 14 Feb"),
        DashboardHistoryModel("YOG", "DPS", "JUM 13 Feb - SAB 14 Feb"),
        DashboardHistoryModel("BAL", "SUR", "JUM 13 Feb - SAB 14 Feb"),
    ))
    private val _popular: MutableLiveData<List<DashboardPopularModel>> = MutableLiveData(listOf(
        DashboardPopularModel("Jakarta"),
        DashboardPopularModel("Yogyakarta"),
        DashboardPopularModel("Bali"),
        DashboardPopularModel("Surabaya"),
    ))

    val mainMenu: LiveData<List<DashboardMainMenuModel>> = _mainMenu
    val recomended: LiveData<List<DashboardRecomendedModel>> = _recomended
    val history: LiveData<List<DashboardHistoryModel>> = _history
    val popular: LiveData<List<DashboardPopularModel>> = _popular
}