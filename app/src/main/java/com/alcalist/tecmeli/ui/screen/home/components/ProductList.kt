package com.alcalist.tecmeli.ui.screen.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alcalist.tecmeli.domain.model.Product

@Composable
fun ProductList(
    products: List<Product>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(products) { product ->
            ProductListItem(
                product = product,
                onItemClick = onItemClick
            )
        }
    }
}
