package id.synrgy6team2.bookingticket.domain.repository

import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LoginRepositoryTest {
   @Mock
   private lateinit var repository: AuthenticationRepository
   private lateinit var useCase: AuthenticationUseCase

   @Before
   fun setup() {
      MockitoAnnotations.openMocks(this)
      useCase = AuthenticationUseCase(repository)
   }

   @After
   fun down() {
      MockitoAnnotations.openMocks(this).close()
   }

   @Test
   fun `login success`() = runTest {
      val user = LoginRequestModel(email = "aryarezza@email.com", password = "Lorem123")
      val response = LoginResponseModel(
         status = "Success",
         LoginResponseModel.LoginResultResponseModel(
            email = "aryarezza@email.com",
            listOf(""),
            token = ""
         )
      )
      `when`(repository.login(user)).thenReturn(response)
      val state = useCase.executeLogin(user)
      Assert.assertEquals(response, state)
   }

   @Test
   fun `login failed`() = runTest {
      val user = LoginRequestModel()
      val response = "Invalid email or password"
      `when` (repository.login(user)).thenThrow(
         RuntimeException(response)
      )
      try {
         useCase.executeLogin(user)
      } catch (e: RuntimeException) {
         Assert.assertEquals(response, e.message)
      }
   }
}