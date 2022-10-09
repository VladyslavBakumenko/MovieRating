package com.example.movierating.data.repositorys.userRepository

import com.example.movierating.data.internet.requestResults.sessionRequestResult.MovieToken
import com.example.movierating.data.internet.requestResults.sessionRequestResult.SessionId
import com.example.movierating.data.internet.requests.AuthRequest
import com.example.movierating.data.internet.requests.RequestToken
import com.example.movierating.data.internet.requests.SessionIdRequest

interface UserRepository {

    suspend fun getRequestToken() : MovieToken?

    suspend fun createSessionWithLogin(request: AuthRequest) : MovieToken?

    suspend fun createNewSession(token: RequestToken) : SessionId?

    suspend fun deleteSession(sessionId: SessionIdRequest)
}