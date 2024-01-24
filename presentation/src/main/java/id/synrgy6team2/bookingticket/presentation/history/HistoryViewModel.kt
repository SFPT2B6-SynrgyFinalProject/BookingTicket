package id.synrgy6team2.bookingticket.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {
    private val _list: MutableLiveData<List<HistoryModel>> = MutableLiveData()

    val cancelHistory: LiveData<List<HistoryModel>> = _list
    val finishHistory: LiveData<List<HistoryModel>> = _list
    val commingHistory: LiveData<List<HistoryModel>> = _list

    fun list() {
        _list.value = listOf(
            HistoryModel(1),
            HistoryModel(2),
            HistoryModel(3),
            HistoryModel(4),
            HistoryModel(5),
        )
    }
}