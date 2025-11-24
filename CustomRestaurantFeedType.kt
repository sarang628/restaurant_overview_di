package com.sarang.torang.di.restaurant_overview_di

import com.sarang.torang.compose.feed.FeedItem
import com.sarang.torang.compose.feed.FeedItemClickEvents
import com.sarang.torang.compose.restaurantdetail.feed.RestaurantFeedType
import com.sarang.torang.data.FeedInRestaurant
import com.sarang.torang.data.feed.FeedImage
import com.sarang.torang.di.feed_di.toReview

val CustomRestaurantFeedType: RestaurantFeedType = {
    FeedItem(
        uiState = it.feed.toFeed.toReview(it.isLogin),
        pageScrollAble = it.pageScrollAble,
        feedItemClickEvents = FeedItemClickEvents(
            onLike = { it.onLike(it.feed.reviewId) },
            onFavorite = { it.onFavorite(it.feed.reviewId) }
        ),
        onPage = {}
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
        createDate =  this.createDate,
        reviewImages = this.reviewImages.map { FeedImage(it.url, it.height, it.width) }
    )
}