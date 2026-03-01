package com.alcalist.tecmeli.ui.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.alcalist.tecmeli.ui.screen.home.HomeScreen
import com.alcalist.tecmeli.ui.screen.product_detail.ProductDetailScreen

@Composable
fun NavigationWrapper() {
    val backStack = remember { mutableStateListOf<Routes>(Routes.Home) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = appEntryProvider(backStack),
        transitionSpec = { slideIn() },
        popTransitionSpec = { slideOut() },
        predictivePopTransitionSpec = { slideOut() }
    )
}

private fun appEntryProvider(backStack: MutableList<Routes>) = entryProvider {
    entry<Routes.Home> {
        HomeScreen(
            navigateToDetail = { id ->
                backStack.add(Routes.ProductDetail(id))
            }
        )
    }

    entry<Routes.ProductDetail> { key ->
        ProductDetailScreen(
            id = key.productId,
            onBack = { backStack.removeLastOrNull() }
        )
    }

    entry<Routes.Error> {
        Text("error")
    }
}

private fun slideIn(): ContentTransform {
    return slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(250)
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(250)
    )
}

private fun slideOut(): ContentTransform {
    return slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(250)
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(250)
    )
}
