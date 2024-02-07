package id.synrgy6team2.bookingticket.presentation.invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.domain.repository.AccountUseCase
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    useCase: AccountUseCase
) : ViewModel() {
    val profile = useCase.executeProfile()
}