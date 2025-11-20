package com.sarang.torang.di.restaurant_overview_di

import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.LikeRepository
import com.sarang.torang.usecase.restaurantoverview.OnLikeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class OnLikeUseCaseImpl {
    @Provides
    fun provideOnLikeUseCase(
        likeRepository: LikeRepository
    ): OnLikeUseCase {
        return object : OnLikeUseCase {
            override suspend fun invoke(reviewId: Int) {
                likeRepository.addLike(reviewId)
            }
        }
    }
}