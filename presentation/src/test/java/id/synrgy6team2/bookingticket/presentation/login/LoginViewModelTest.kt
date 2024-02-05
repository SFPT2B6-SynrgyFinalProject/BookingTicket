package id.synrgy6team2.bookingticket.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import getOrAwaitValue
import id.synrgy6team2.bookingticket.common.MainDispatcherRule
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var authenticationUseCase: AuthenticationUseCase

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(authenticationUseCase)
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

    @Test
    fun `login success`() = runTest {
        val loginRequest = LoginRequestModel(email = "ali@gmail.com", password = "password")
        val loginResponse = LoginResponseModel(
            status = "success",
            data = LoginResponseModel.LoginResultResponseModel(
                email = "ali@gmail.com",
                roles = listOf("user"),
                token = "token"
            )
        )

        `when`(authenticationUseCase.executeLogin(loginRequest)).thenReturn(loginResponse)

        viewModel.login(loginRequest)

        val value = viewModel.login.getOrAwaitValue()

        assertTrue(value is State.Success)
        assertEquals(value.data, loginResponse)
    }


    @Test
    fun `login failed`() = runTest {
        val loginRequestModel = LoginRequestModel()
        val errorMessage = "Invalid email or password"

        `when`(authenticationUseCase.executeLogin(loginRequestModel)).thenThrow(
            RuntimeException(
                errorMessage
            )
        )

        viewModel.login(loginRequestModel)

        val value = viewModel.login.getOrAwaitValue()

        assertTrue(value is State.Error)
        assertEquals(errorMessage, value.message)
    }

}