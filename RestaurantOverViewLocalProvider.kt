package com.sarang.torang.di.restaurant_overview_di

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.CompositionLocalProvider
import com.sarang.torang.LocalRestaurantInfoImageLoader
import com.sarang.torang.RestaurantInfoData
import com.sarang.torang.RestaurantInfoImageLoader
import com.sarang.torang.RestaurantInfoScreenData
import com.sarang.torang.RestaurantInfoViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.type.RestaurantOverViewImageLoader
import com.sarang.torang.compose.type.RestaurantOverviewRestaurantInfo
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.restauarnt_info_di.restaurantInfo

val restaurantOverViewImageLoader: RestaurantOverViewImageLoader = { modifier, url, width, height, scale ->
    // 여기서 실제 이미지 로딩 구현 예시
    provideTorangAsyncImage().invoke(modifier, url, width, height, scale)
}

@RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
fun restaurantOverViewRestaurantInfo(rootNavController: RootNavController, viewModel : RestaurantInfoViewModel): RestaurantOverviewRestaurantInfo = {
    CompositionLocalProvider(LocalRestaurantInfoImageLoader provides restaurantInfoImageLoader){
        restaurantInfo(viewModel = viewModel).invoke(
            RestaurantInfoScreenData(
                restaurantId = it,
                onLocation = {
                    rootNavController.map(it)
                }
            )
        )
    }
}

val restaurantInfoImageLoader: RestaurantInfoImageLoader = { modifier, url, width, height, scale ->
    // 여기서 실제 이미지 로딩 구현 예시
    provideTorangAsyncImage().invoke(modifier, url, width, height, scale)
}