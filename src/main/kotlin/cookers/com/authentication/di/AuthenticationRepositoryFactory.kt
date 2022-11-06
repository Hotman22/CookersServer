package cookers.com.authentication.di

import cookers.com.authentication.data.data_source.AuthenticationDb
import cookers.com.authentication.data.data_source.RefreshTokenDb
import cookers.com.authentication.data.repository.AuthenticationRepositoryImpl

object AuthenticationRepositoryFactory {
    fun make() = AuthenticationRepositoryImpl(AuthenticationDb(), RefreshTokenDb())
}