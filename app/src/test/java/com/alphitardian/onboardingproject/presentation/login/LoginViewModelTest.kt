package com.alphitardian.onboardingproject.presentation.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.data.auth.repository.AuthRepositoryImpl
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.repository.AuthRepository
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.user_login.UserLoginUseCase
import com.alphitardian.onboardingproject.utils.DummyData
import com.alphitardian.onboardingproject.utils.FakeAndroidKeyStore
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class LoginViewModelTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var viewModel: LoginViewModel

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var loginUseCase: UserLoginUseCase

    @Mock
    private lateinit var encryptTokenUseCase: EncryptTokenUseCase

    @Mock
    private lateinit var observer: Observer<Resource<TokenResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        authRepository = Mockito.mock(AuthRepositoryImpl::class.java)
        loginUseCase = Mockito.mock(UserLoginUseCase::class.java)
        encryptTokenUseCase = Mockito.mock(EncryptTokenUseCase::class.java)
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            encryptTokenUseCase = encryptTokenUseCase,
            datastore = PrefStore(context)
        )
    }

    @Test
    fun testLoginUserSuccess() {
        runBlocking {
            val dummyResponse = MutableLiveData<Resource<TokenResponse>>()
            dummyResponse.value =
                Resource.Success<TokenResponse>(data = DummyData.expectedTokenResponse)

            viewModel.email.value = "tester"
            viewModel.password.value = "tester123"

            Mockito.`when`(authRepository.loginUser(LoginRequest("tester123", "tester")))
                .thenReturn(
                    DummyData.expectedTokenResponse)
            Mockito.`when`(loginUseCase.invoke(LoginRequest("tester123",
                "tester"))).thenReturn(DummyData.expectedTokenResponse)

            viewModel.loginUser()

            viewModel.loginState.observeForever(observer)
            Mockito.verify(observer).onChanged(dummyResponse.value)
        }
    }
}