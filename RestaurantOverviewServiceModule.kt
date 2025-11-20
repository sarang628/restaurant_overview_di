package com.sarang.torang.di.restaurant_overview_di

import com.sarang.torang.data.FeedInRestaurant
import com.sarang.library.data.MenuData
import com.sarang.library.data.RestaurantImage
import com.sarang.torang.usecase.restaurantoverview.FetchReviewsUseCase
import com.sarang.torang.usecase.restaurantoverview.GetMenuUseCase
import com.sarang.torang.usecase.restaurantoverview.GetRestaurantGalleryUseCase
import com.sarang.torang.api.ApiRestaurant
import com.sarang.torang.api.ApiReview
import com.sarang.torang.api.handle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException

@InstallIn(SingletonComponent::class)
@Module
class RestaurantOverviewServiceModule {
    @Provides
    fun providesFetchReviewsUseCase(apiReview: ApiReview): FetchReviewsUseCase {
        return object : FetchReviewsUseCase {
            override suspend fun invoke(restaurantId: Int): List<FeedInRestaurant> {
                try {
                    return apiReview.getReviews(restaurantId).map { it.toFeedData() }
                } catch (e: HttpException) {
                    throw Exception(e.handle())
                }
            }
        }
    }

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