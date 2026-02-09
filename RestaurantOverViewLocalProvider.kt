package com.sarang.torang.di.restaurant_overview_di

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sarang.torang.LocalRestaurantInfoImageLoader
import com.sarang.torang.RestaurantInfoData
import com.sarang.torang.RestaurantInfoImageLoader
import com.sarang.torang.RestaurantInfoScreenData
import com.sarang.torang.RestaurantInfoViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.type.RestaurantOverViewImageLoader
import com.sarang.torang.compose.type.RestaurantOverviewRestaurantInfo
import com.sarang.torang.di.image.TorangAsyncImageData
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.restauarnt_info_di.restaurantInfo

val restaurantOverViewImageLoader: RestaurantOverViewImageLoader = { modifier, url, progressSize, errorIconSize, contentScale ->
    provideTorangAsyncImage().invoke(
        TorangAsyncImageData(
            modifier = modifier,
            model = url,
            progressSize = progressSize ?: 30.dp,
            errorIconSize = errorIconSize ?: 30.dp,
            contentScale = contentScale ?: ContentScale.None
        )
    )
}

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

val restaurantInfoImageLoader: RestaurantInfoImageLoader = { modifier, url, progressSize, errorIconSize, contentScale ->
    provideTorangAsyncImage().invoke(
        TorangAsyncImageData(
            modifier = modifier,
            model = url,
            progressSize = progressSize ?: 30.dp,
            errorIconSize = errorIconSize ?: 30.dp,
            contentScale = contentScale ?: ContentScale.None
        )
    )
}