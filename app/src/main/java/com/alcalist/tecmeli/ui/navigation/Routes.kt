package com.alcalist.tecmeli.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes: NavKey {
    data object Home : Routes()

    data class ProductDetail(val productId: String) : Routes()

    @Serializable
    data object Error : Routes()
}