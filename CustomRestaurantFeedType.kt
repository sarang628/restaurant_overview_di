package com.sarang.torang.di.restaurant_overview_di

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.FeedItem
import com.sarang.torang.compose.restaurantdetail.feed.RestaurantFeedType
import com.sarang.torang.data.FeedInRestaurant
import com.sarang.torang.data.basefeed.FeedItemClickEvents
import com.sarang.torang.data.feed.FeedImage
import com.sarang.torang.di.feed_di.toReview

private val tag = "__CustomRestaurantFeedType"
fun customRestaurantFeedType(
    rootNavController : RootNavController = RootNavController(),
    onComment    : (Int) -> Unit = { Log.w(tag, "onComment callback is not set") },
    onShare      : (Int) -> Unit = { Log.w(tag, "onShare callback is not set") },
    onMenu       : (Int) -> Unit = { Log.w(tag, "onMenu callback is not set") },
): RestaurantFeedType = { feedData ->
    var lastPage : Int by remember { mutableStateOf(0) }
    FeedItem(
        uiState = feedData.feed.toFeed.toReview(feedData.isLogin),
        pageScroll = feedData.pageScrollAble,
        feedItemClickEvents = FeedItemClickEvents(
            onLike       = { feedData.onLike(feedData.feed.reviewId) },
            onFavorite   = { feedData.onFavorite(feedData.feed.reviewId) },
            onComment    = { onComment(feedData.feed.reviewId) },
            onShare      = { onShare(feedData.feed.reviewId) },
            onMenu       = { onMenu(feedData.feed.reviewId) },
            onLikes      = { rootNavController.like(feedData.feed.reviewId) },
            onImage      = { rootNavController.imagePager(feedData.feed.reviewId, lastPage) },
            onName       = { rootNavController.profile(feedData.feed.userId) },
            onProfile    = { rootNavController.profile(feedData.feed.userId) },
            onRestaurant = { rootNavController.restaurant(feedData.feed.restaurantId) }
        ),
        onPage = {
            lastPage = it.page
        }
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
        reviewImages = this.reviewImages.map { FeedImage(
            url = it.url,
            width = it.width,
            height = it.height)
        }
    )
}