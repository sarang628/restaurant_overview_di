package com.sarang.torang.di.restaurant_overview_di

import com.sarang.torang.core.database.dao.LikeDao
import com.sarang.torang.usecase.AddLikeUseCase
import com.sarang.torang.usecase.DeleteLikeUseCase
import com.sarang.torang.usecase.restaurantoverview.ClickLikeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ClickLikeUseCaseImpl {

    val tag = "__ClickLikeUseCaseImpl"
    @Provides
    fun provideOnLikeUseCase(
        likeDao: LikeDao,
        addLikeUseCase: AddLikeUseCase,
        deleteLikeUseCase: DeleteLikeUseCase
    ): ClickLikeUseCase {
        return object : ClickLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                val count = likeDao.has(reviewId)
                if (count == 0) {
                    addLikeUseCase.invoke(reviewId)
                } else {
                    deleteLikeUseCase.invoke(reviewId)
                }
            }
        }
    }
}