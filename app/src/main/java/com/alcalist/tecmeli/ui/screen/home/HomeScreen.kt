package com.alcalist.tecmeli.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alcalist.tecmeli.core.network.AppError
import com.alcalist.tecmeli.core.util.UiState
import com.alcalist.tecmeli.ui.components.ErrorState
import com.alcalist.tecmeli.ui.screen.home.components.ProductList
import com.alcalist.tecmeli.ui.screen.home.components.SearchBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBarComponent(
                query = query,
                onQueryChange = { query = it },
                onSearch = { 
                    viewModel.searchProducts(it)
                    active = false 
                },
                active = active,
                onActiveChange = { active = it }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = uiState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                    is UiState.Success -> {
                        ProductList(products = state.data, onItemClick = navigateToDetail)
                    }
                    is UiState.Error -> {
                        ErrorState(
                            error = (state.exception as? AppError) ?: AppError.Unknown(state.exception ?: Exception(state.message)),
                            onRetry = { viewModel.searchProducts(query) }
                        )
                    }
                    is UiState.Empty -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = if (query.isEmpty()) "Comienza a buscar productos" else "No se encontraron resultados para \"$query\"",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
