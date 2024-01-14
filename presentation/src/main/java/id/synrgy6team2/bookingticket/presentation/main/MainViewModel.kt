package id.synrgy6team2.bookingticket.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {
    fun logout(value: GoogleSignInClient) {
        viewModelScope.launch {
            try {
                authenticationUseCase.executeLogout()
                withContext(Dispatchers.IO) {
                    value.signOut().await()
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }
}