package com.alphitardian.onboardingproject.domain.use_case.get_token

import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.remote.entity.auth.TokenResponse
import com.alphitardian.onboardingproject.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(userToken : String): Flow<Resource<TokenResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getUserToken(userToken)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}