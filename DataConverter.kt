package com.sarang.torang.di.restaurant_overview_di

import com.sarang.library.data.Feed
import com.sarang.library.data.MenuData
import com.sarang.library.data.RestaurantImage
import com.sarang.library.data.ReviewRowData
import com.sarang.library.data.ReviewSummaryData
import com.sarang.torang.BuildConfig
import com.sarang.torang.data.RestaurantDetail
import com.sarang.torang.data.remote.response.FeedApiModel

fun RestaurantDetail.toMenus(): List<MenuData> {
    return this.menus.map {
        MenuData(
            menuName = it.menu_name,
            price = it.menu_price.toFloat(),
            url = BuildConfig.MENU_IMAGE_SERVER_URL + it.menu_pic_url
        )
    }
}

fun RestaurantDetail.toRestaurantImages(): List<RestaurantImage> {
    return this.pictures.map {
        RestaurantImage(
            id = it.picture_id,
            url = BuildConfig.REVIEW_IMAGE_SERVER_URL + it.picture_url
        )
    }
}

fun RestaurantDetail.toReviewSummaryData(): ReviewSummaryData {
    return ReviewSummaryData(
        rating = this.restaurant.rating,
        totalReviewer = this.restaurant.reviewCount,
        score5 = 5.0f,
        score4 = 4.0f,
        score3 = 3.0f,
        score2 = 2.0f,
        score1 = 1.0f
    )
}

fun RestaurantDetail.toReviewRowData(): List<ReviewRowData> {
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

fun FeedApiModel.toFeedData(): Feed {
    return Feed(
        reviewId = this.reviewId,
        userId = this.user.userId,
        name = this.user.userName,
        restaurantName = this.restaurant.restaurantName,
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
        reviewImages = this.pictures.map { BuildConfig.REVIEW_IMAGE_SERVER_URL + it.picture_url },
        restaurantId = this.restaurant.restaurantId
    )
}