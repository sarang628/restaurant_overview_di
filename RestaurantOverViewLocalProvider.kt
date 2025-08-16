package com.sarang.torang.di.restaurant_overview_di

import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.LocalRestaurantInfoImageLoader
import com.sarang.torang.RestaurantInfoImageLoader
import com.sarang.torang.compose.type.RestaurantOverViewImageLoader
import com.sarang.torang.compose.type.RestaurantOverviewRestaurantInfo
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.restaurant_info.RestaurantInfoWithPermission
import com.sarang.torang.di.restaurant_info.RestaurantInfoWithPermissionWithLocation
import com.sryang.library.compose.workflow.BestPracticeViewModel

val restaurantOverViewImageLoader: RestaurantOverViewImageLoader = { modifier, url, width, height, scale ->
    // 여기서 실제 이미지 로딩 구현 예시
    provideTorangAsyncImage().invoke(modifier, url, width, height, scale)
}

val restaurantOverViewRestaurantInfo: RestaurantOverviewRestaurantInfo = {
    CompositionLocalProvider(LocalRestaurantInfoImageLoader provides restaurantInfoImageLoader){
        RestaurantInfoWithPermissionWithLocation(restaurantId = it)
    }
}

val restaurantInfoImageLoader: RestaurantInfoImageLoader = { modifier, url, width, height, scale ->
    // 여기서 실제 이미지 로딩 구현 예시
    provideTorangAsyncImage().invoke(modifier, url, width, height, scale)
}