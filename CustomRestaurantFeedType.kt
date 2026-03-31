package com.sarang.torang.di.restaurant_overview_di

import android.util.Log
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.data.Feed
import com.sarang.torang.compose.feed.data.FeedImage
import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.restaurantdetail.feed.RestaurantFeedType
import com.sarang.torang.data.FeedInRestaurant
import com.sarang.torang.di.feed_di.provideFeed

private val tag = "__CustomRestaurantFeedType"
fun customRestaurantFeedType(
    rootNavController : RootNavController = RootNavController(),
    onComment    : (Int) -> Unit = { Log.w(tag, "onComment callback is not set") },
    onShare      : (Int) -> Unit = { Log.w(tag, "onShare callback is not set") },
    onMenu       : (Int) -> Unit = { Log.w(tag, "onMenu callback is not set") },
): RestaurantFeedType = { feedData ->
    provideFeed(
        rootNavController = rootNavController,
        onComment = onComment,
        onShare = onShare,
        onMenu = onMenu
    ).invoke(FeedTypeData(
        feed = feedData.feed.toFeed,
        isLogin = feedData.isLogin,
        pageScrollable = feedData.pageScrollAble
    ))
}

val FeedInRestaurant.toFeed : Feed get() {
    return Feed(
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
                id = 0,
                url = it.url,
                width = it.width,
                height = it.height
            )
        }
    )
}