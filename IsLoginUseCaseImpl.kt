package com.sarang.torang.di.restaurant_overview_di

import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.usecase.restaurantoverview.IsLoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@InstallIn(SingletonComponent::class)
@Module
class IsLoginUseCaseImpl {

    @Provides
    fun provideIsLoginUseCase(
        loginRepository: LoginRepository
    ) : IsLoginUseCase {
        return object : IsLoginUseCase{
            override fun invoke(): Flow<Boolean> {
                return loginRepository.isLogin
            }
        }
    }

}