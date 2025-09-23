package com.sarang.torang.di.restaurant_overview_di

import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.feed.type.feedType
import com.sarang.torang.data.FeedInRestaurant
import com.sarang.torang.compose.restaurantdetail.feed.RestaurantFeedType
import com.sarang.torang.data.ReviewImage
import com.sarang.torang.data.feed.FeedImage
import com.sarang.torang.di.feed_di.provideFeed

val CustomRestaurantFeedType: RestaurantFeedType = {
   feed, onLike, onFavorite, isLogin, onVideoClick, imageHeight, pageScrollAble ->
    provideFeed().invoke(
        FeedTypeData(
            feed = feed.toFeed,
            onLike = onLike,
            onFavorite = onFavorite,
            isLogin = isLogin,
            onVideoClick = onVideoClick,
            imageHeight = imageHeight,
            pageScrollable = pageScrollAble
        )
    )
}

val FeedInRestaurant.toFeed : com.sarang.torang.data.feed.Feed get() {
    return com.sarang.torang.data.feed.Feed(
        reviewId = this.reviewId,
        restaurantId = this.restaurantId,
        userId = this.userId,
        name = this.name,
        restaurantName = this.restaurantName,
        rating = this.rating,
        profilePictureUrl = this.profilePictureUrl,
        likeAmount = this.likeAmount,
        commentAmount = this.commentAmount,
        isLike = this.isLike,
        isFavorite = this.isFavorite,
        contents = this.contents,
        createDate =  "",
        reviewImages = this.reviewImages.map { FeedImage(it.url, it.height, it.width) }
    )
}