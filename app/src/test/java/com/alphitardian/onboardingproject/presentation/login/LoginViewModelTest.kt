package com.alphitardian.onboardingproject.presentation.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.LoginRequest
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.user_login.UserLoginUseCase
import com.alphitardian.onboardingproject.utils.DummyData
import com.alphitardian.onboardingproject.utils.FakeAndroidKeyStore
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
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
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

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
    private lateinit var loginUseCase: UserLoginUseCase

    @Mock
    private lateinit var encryptTokenUseCase: EncryptTokenUseCase

    @Mock
    private lateinit var observer: Observer<Resource<TokenResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

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
            val requestBody = LoginRequest("tester123", "tester")

            viewModel.email.value = requestBody.username
            viewModel.password.value = requestBody.password

            Mockito.`when`(loginUseCase.invoke(requestBody))
                .thenReturn(DummyData.expectedTokenResponse)

            viewModel.loginUser()

            viewModel.loginState.observeForever(observer)
            Mockito.verify(observer).onChanged(dummyResponse.value)
        }
    }

    @Test
    fun testLoginUserFailed_error401() {
        runBlocking {
            val response =
                File("${DummyData.BASE_PATH}login-response-401.json").inputStream().readBytes()
                    .toString(Charsets.UTF_8)
            val exception = HttpException(Response.error<TokenResponse>(401,
                response.toResponseBody("plain/text".toMediaTypeOrNull())))

            val dummyResponse = MutableLiveData<Resource<TokenResponse>>()
            dummyResponse.value = Resource.Error(code = 401)
            val requestBody = LoginRequest("tester", "tester")

            viewModel.email.value = requestBody.username
            viewModel.password.value = requestBody.password

            Mockito.`when`(loginUseCase.invoke(requestBody)).thenThrow(exception)

            viewModel.loginUser()

            viewModel.mutableLoginState.observeForever(observer)

            Mockito.verify(observer).onChanged(dummyResponse.value)
        }
    }

    @Test
    fun testLoginUserFailed_error422() {
        runBlocking {
            val response =
                File("${DummyData.BASE_PATH}login-response-422.json").inputStream().readBytes()
                    .toString(Charsets.UTF_8)
            val exception = HttpException(Response.error<TokenResponse>(422,
                response.toResponseBody("plain/text".toMediaTypeOrNull())))

            val dummyResponse = MutableLiveData<Resource<TokenResponse>>()
            dummyResponse.value = Resource.Error(error = exception, code = 422)
            val requestBody = LoginRequest("", "")

            viewModel.email.value = requestBody.username
            viewModel.password.value = requestBody.password

            Mockito.`when`(loginUseCase.invoke(requestBody)).thenThrow(exception)

            viewModel.loginUser()

            viewModel.mutableLoginState.observeForever(observer)

            Mockito.verify(observer).onChanged(dummyResponse.value)
        }
    }
}