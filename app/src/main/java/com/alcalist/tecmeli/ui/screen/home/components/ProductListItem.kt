package com.alcalist.tecmeli.ui.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alcalist.tecmeli.core.util.DateUtils
import com.alcalist.tecmeli.domain.model.Product

@Composable
fun ProductListItem(
    product: Product,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = { 
            Text(text = product.title) 
        },
        supportingContent = { 
            Text(text = "Última actualización: ${DateUtils.formatDate(product.lastUpdated)}")
        },
        leadingContent = {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        },
        modifier = modifier.clickable { onItemClick(product.id) }
    )
    HorizontalDivider()
}
