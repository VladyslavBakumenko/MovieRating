package com.example.movierating.data.repositorys.userRepository

import com.example.movierating.data.internet.api.ApiFactory
import com.example.movierating.data.internet.requestResults.sessionRequestResult.MovieToken
import com.example.movierating.data.internet.requestResults.sessionRequestResult.SessionId
import com.example.movierating.data.internet.requests.AuthRequest
import com.example.movierating.data.internet.requests.RequestToken
import com.example.movierating.data.internet.requests.SessionIdRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor () : UserRepository {

    override suspend fun getRequestToken(): MovieToken? =
        ApiFactory.movieApi.getRequestToken().body()


    override suspend fun createSessionWithLogin(request: AuthRequest): MovieToken? = ApiFactory
        .movieApi.createSessionWithLogin(request = request).body()


    override suspend fun createNewSession(token: RequestToken): SessionId? = ApiFactory.movieApi
        .createNewSession(token = token).body()


    override suspend fun deleteSession(sessionId: SessionIdRequest) {
        ApiFactory.movieApi.deleteSession(sessionId = sessionId)
    }
}