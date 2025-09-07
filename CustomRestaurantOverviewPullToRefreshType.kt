package com.sarang.torang.di.restaurant_overview_di

import com.sarang.torang.compose.type.PullToRefresh
import com.sarang.torang.di.pulltorefresh.providePullToRefresh
import com.sryang.library.pullrefresh.rememberPullToRefreshState

val CustomRestaurantOverviewPullToRefreshType: PullToRefresh = { modifier, isRefreshing, onRefresh, contents ->
    providePullToRefresh(rememberPullToRefreshState()).invoke(modifier, isRefreshing, onRefresh, contents)
}