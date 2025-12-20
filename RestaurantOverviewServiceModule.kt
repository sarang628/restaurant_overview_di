package com.sarang.torang.di.restaurant_overview_di

import com.sarang.library.data.MenuData
import com.sarang.library.data.RestaurantImage
import com.sarang.torang.BuildConfig
import com.sarang.torang.api.ApiRestaurant
import com.sarang.torang.api.handle
import com.sarang.torang.data.FeedImageInRestaurant
import com.sarang.torang.data.FeedInRestaurant
import com.sarang.torang.data.ReviewAndImage
import com.sarang.torang.data.ReviewImage
import com.sarang.torang.repository.feed.FeedFlowRepository
import com.sarang.torang.repository.feed.FeedLoadRepository
import com.sarang.torang.usecase.restaurantoverview.FetchReviewsUseCase
import com.sarang.torang.usecase.restaurantoverview.GetMenuUseCase
import com.sarang.torang.usecase.restaurantoverview.GetRestaurantGalleryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

@InstallIn(SingletonComponent::class)
@Module
class RestaurantOverviewServiceModule {
    @Provides
    fun providesFetchReviewsUseCase(
        feedLoadRepository  : FeedLoadRepository,
        feedFlowRepository  : FeedFlowRepository
    ): FetchReviewsUseCase {
        return object : FetchReviewsUseCase {
            override suspend fun invoke(restaurantId: Int): Flow<List<FeedInRestaurant>> {
                try {
                    feedLoadRepository.loadByRestaurantId(restaurantId)
                } catch (e: HttpException) {
                    throw Exception(e.handle())
                }

                return feedFlowRepository.findRestaurantFeedsFlow(restaurantId).map {
                    it.map {
                        it.toFeedInRestaurant()
                    }
                }
            }
        }
    }

    fun ReviewAndImage.toFeedInRestaurant() : FeedInRestaurant {
        return FeedInRestaurant(
            restaurantId = this.review.restaurantId ?: 0,
            reviewId = this.review.reviewId,
            userId = this.review.userId ?: 0,
            name = this.review.userName ?: "",
            restaurantName = this.review.restaurantName ?: "",
            rating = this.review.rating ?: 0f,
            profilePictureUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL + this.review.profilePicUrl,
            likeAmount = this.review.likeAmount ?: 0,
            commentAmount = this.review.commentAmount ?: 0,
            author = "",
            author1 = "",
            author2 = "",
            comment = "",
            comment1 = "",
            comment2 = "",
            isLike = this.like != null,
            isFavorite = this.favorite != null,
            visibleLike = (review.likeAmount ?: 0) > 0,
            visibleComment = (review.commentAmount ?: 0) > 0,
            contents = this.review.contents ?: "",
            createDate = this.review.createDate ?: "",
            reviewImages = this.images.map { it.reviewImage }
        )
    }

    val ReviewImage.reviewImage : FeedImageInRestaurant get() =  FeedImageInRestaurant(
        url = BuildConfig.REVIEW_IMAGE_SERVER_URL + this.pictureUrl,
        width = this.width ?: 0,
        height = this.height ?: 0
    )

    @Provides
    fun providesGetRestaurantGalleryUseCase(
        apiRestaurant: ApiRestaurant,
    ): GetRestaurantGalleryUseCase {
        return object : GetRestaurantGalleryUseCase {
            override suspend fun invoke(restaurantId: Int): List<RestaurantImage> {
                return apiRestaurant.getRestaurantDetail(restaurantId).toRestaurantImages()
            }
        }
    }

    @Provides
    fun providesGetMenuUseCase(apiRestaurant: ApiRestaurant): GetMenuUseCase {
        return object : GetMenuUseCase {
            override suspend fun invoke(restaurantId: Int): List<MenuData> {
                return apiRestaurant.getRestaurantDetail(restaurantId).toMenus()
            }
        }
    }
}