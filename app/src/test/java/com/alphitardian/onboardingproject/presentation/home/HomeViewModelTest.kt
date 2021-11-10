package com.alphitardian.onboardingproject.presentation.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.UserEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.toNewsEntity
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.toUserEntity
import com.alphitardian.onboardingproject.data.user.repository.UserRepositoryImpl
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.domain.repository.UserRepository
import com.alphitardian.onboardingproject.domain.use_case.check_user_login_time.CheckUserLoginTimeUseCase
import com.alphitardian.onboardingproject.domain.use_case.encrypt_token.EncryptTokenUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_news.GetNewsUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_profile.GetProfileUseCase
import com.alphitardian.onboardingproject.domain.use_case.get_token.GetTokenUseCase
import com.alphitardian.onboardingproject.utils.DummyData
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
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
class HomeViewModelTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var profileUseCase: GetProfileUseCase

    @Mock
    private lateinit var newsUseCase: GetNewsUseCase

    @Mock
    private lateinit var tokenUseCase: GetTokenUseCase

    @Mock
    private lateinit var encryptTokenUseCase: EncryptTokenUseCase

    @Mock
    private lateinit var checkUserLoginTimeUseCase: CheckUserLoginTimeUseCase

    @Mock
    private lateinit var profileObserver: Observer<Resource<UserEntity>>

    @Mock
    private lateinit var newsObserver: Observer<Resource<List<NewsEntity>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        userRepository = Mockito.mock(UserRepositoryImpl::class.java)
        profileUseCase = Mockito.mock(GetProfileUseCase::class.java)
        newsUseCase = Mockito.mock(GetNewsUseCase::class.java)
        tokenUseCase = Mockito.mock(GetTokenUseCase::class.java)
        encryptTokenUseCase = Mockito.mock(EncryptTokenUseCase::class.java)
        checkUserLoginTimeUseCase = Mockito.mock(CheckUserLoginTimeUseCase::class.java)

        viewModel = HomeViewModel(
            tokenUseCase = tokenUseCase,
            newsUseCase = newsUseCase,
            profileUseCase = profileUseCase,
            encryptTokenUseCase = encryptTokenUseCase,
            checkUserLoginTimeUseCase = checkUserLoginTimeUseCase,
            datastore = PrefStore(context)
        )
    }

    @Test
    fun testGetProfileSuccess() {
        runBlocking {
            val dummyResponse = MutableLiveData<Resource<UserEntity>>()
            dummyResponse.value =
                Resource.Success<UserEntity>(data = DummyData.expectedProfileResponse.toUserEntity())

            Mockito.`when`(userRepository.getUserProfile())
                .thenReturn(DummyData.expectedProfileResponse.toUserEntity())
            Mockito.`when`(profileUseCase.invoke())
                .thenReturn(DummyData.expectedProfileResponse.toUserEntity())

            viewModel.getUserProfile()

            delay(1000)
            viewModel.profile.observeForever(profileObserver)
            Mockito.verify(profileObserver).onChanged(dummyResponse.value)
        }
    }

    @Test
    fun testGetNewsSuccess() {
        runBlocking {
            val dummyResponse = MutableLiveData<Resource<List<NewsEntity>>>()
            val dummyData = DummyData.expectedNewsResponse.data.map { it.toNewsEntity() }
            dummyResponse.value = Resource.Success<List<NewsEntity>>(data = dummyData)

            Mockito.`when`(userRepository.getNews()).thenReturn(dummyData)
            Mockito.`when`(newsUseCase.invoke()).thenReturn(dummyData)

            viewModel.getUserNews()

            delay(1000)
            viewModel.news.observeForever(newsObserver)
            Mockito.verify(newsObserver).onChanged(dummyResponse.value)
        }
    }
}