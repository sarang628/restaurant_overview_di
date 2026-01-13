package com.sarang.torang.di.restaurant_overview_di

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.RestaurantInfoViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.internal.components.type.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.type.LocalFeedImageLoader
import com.sarang.torang.compose.restaurantdetail.RestaurantOverViewScreen
import com.sarang.torang.compose.restaurantdetail.feed.LocalRestaurantFeed
import com.sarang.torang.compose.type.LocalPullToRefresh
import com.sarang.torang.compose.type.LocalRestaurantOverViewImageLoader
import com.sarang.torang.compose.type.LocalRestaurantOverviewRestaurantInfo
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvideRestaurantOverview(
    restaurantId    : Int,
    onErrorMessage  : (String) -> Unit = {},
    rootNavController: RootNavController = RootNavController()
){
    val viewModel : RestaurantInfoViewModel = hiltViewModel()
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader    provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo(
            rootNavController = rootNavController,
            viewModel = viewModel
        ),
        LocalRestaurantFeed                   provides customRestaurantFeedType(
            rootNavController = rootNavController
        ),
        LocalExpandableTextType               provides CustomExpandableTextType,
        LocalFeedImageLoader                  provides CustomFeedImageLoader(),
        LocalPullToRefresh                    provides CustomRestaurantOverviewPullToRefreshType
    ) {
        Box(Modifier.fillMaxSize()){
            RestaurantOverViewScreen(
                restaurantId            = restaurantId,
                onRefresh               = {viewModel.refresh(restaurantId)},
                isRefreshRestaurantInfo = viewModel.isRefresh,
                onErrorMessage          = onErrorMessage
            )
        }
    }
}