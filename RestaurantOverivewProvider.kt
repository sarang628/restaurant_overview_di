package com.sarang.torang.di.restaurant_overview_di

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.FeedDialogsViewModel
import com.sarang.torang.RestaurantInfoViewModel
import com.sarang.torang.RootNavController
import com.sarang.torang.compose.feed.internal.components.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.LocalFeedImageLoader
import com.sarang.torang.compose.restaurantdetail.RestaurantOverViewScreen
import com.sarang.torang.compose.restaurantdetail.feed.LocalRestaurantFeed
import com.sarang.torang.compose.type.LocalPullToRefresh
import com.sarang.torang.compose.type.LocalRestaurantOverViewImageLoader
import com.sarang.torang.compose.type.LocalRestaurantOverviewRestaurantInfo
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.dialogsbox_di.ProvideMainDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvideRestaurantOverview(
    restaurantId    : Int,
    onErrorMessage  : (String) -> Unit = {}
){
    val viewModel : RestaurantInfoViewModel = hiltViewModel()
    val dialogsViewModel : FeedDialogsViewModel = hiltViewModel()
    CompositionLocalProvider(
        LocalRestaurantOverViewImageLoader    provides restaurantOverViewImageLoader,
        LocalRestaurantOverviewRestaurantInfo provides restaurantOverViewRestaurantInfo(
            rootNavController = RootNavController(),
            viewModel = viewModel
        ),
        LocalRestaurantFeed                   provides customRestaurantFeedType(
            onComment = { dialogsViewModel.onComment(it) },
            onShare = { dialogsViewModel.onShare(it) },
            onMenu = { dialogsViewModel.onMenu(it) }
        ),
        LocalExpandableTextType               provides CustomExpandableTextType,
        LocalFeedImageLoader                  provides CustomFeedImageLoader(),
        LocalPullToRefresh                    provides CustomRestaurantOverviewPullToRefreshType
    ) {
        Box(Modifier.fillMaxSize()){
            ProvideMainDialog(
                dialogsViewModel = dialogsViewModel
            ) {
                RestaurantOverViewScreen(
                    restaurantId            = restaurantId,
                    onRefresh               = {viewModel.refresh(restaurantId)},
                    isRefreshRestaurantInfo = viewModel.isRefresh,
                    onErrorMessage          = onErrorMessage
                )
            }
        }
    }
}