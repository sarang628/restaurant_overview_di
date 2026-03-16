package com.sarang.torang.di.restaurant_overview_di

import com.sarang.library.data.MenuData
import com.sarang.library.data.RestaurantImage
import com.sarang.library.data.ReviewRowData
import com.sarang.torang.BuildConfig
import com.sarang.torang.data.FeedImageInRestaurant
import com.sarang.torang.data.FeedInRestaurant
import com.sarang.torang.data.remote.response.FeedApiModel
import com.sarang.torang.data.remote.response.RestaurantDetailApiModel

fun RestaurantDetailApiModel.toMenus(): List<MenuData> {
    return this.menus.map {
        MenuData(
            menuName = it.menuName ?: "",
            price = it.menuPrice?.toFloat() ?: 0f,
            url = BuildConfig.MENU_IMAGE_SERVER_URL + it.menuPicUrl
        )
    }
}

fun RestaurantDetailApiModel.toRestaurantImages(): List<RestaurantImage> {
    return this.pictures.map {
        RestaurantImage(
            id = it.picture_id,
            url = BuildConfig.REVIEW_IMAGE_SERVER_URL + it.picture_url
        )
    }
}

fun RestaurantDetailApiModel.toReviewRowData(): List<ReviewRowData> {
    return this.comments.map {
        ReviewRowData(
            name = it.user_name,
            fullName = it.user_name,
            rating = 3.0f,
            comment = it.comment,
            reviewId = it.review_id,
            userId = it.user_id
        )
    }
}

fun FeedApiModel.toFeedInRestaurant(): FeedInRestaurant {
    return FeedInRestaurant(
        reviewId = this.reviewId,
        userId = this.user.userId,
        name = this.user.userName,
        restaurantName = this.restaurant.restaurantName ?: "",
        rating = this.rating,
        profilePictureUrl = BuildConfig.PROFILE_IMAGE_SERVER_URL + this.user.profilePicUrl,
        likeAmount = this.like_amount,
        commentAmount = this.comment_amount,
        author = "",
        author1 = "",
        author2 = "",
        comment = "",
        comment1 = "",
        comment2 = "",
        isLike = this.like != null,
        isFavorite = this.favorite != null,
        visibleLike = false,
        visibleComment = false,
        contents = this.contents,
        reviewImages = this.pictures.map { FeedImageInRestaurant(BuildConfig.REVIEW_IMAGE_SERVER_URL + it.picture_url, it.width, it.height) },
        restaurantId = this.restaurant.restaurantId ?: -1,
        createDate = this.create_date
    )
}