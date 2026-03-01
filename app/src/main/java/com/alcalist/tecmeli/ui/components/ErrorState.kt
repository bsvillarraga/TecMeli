package com.alcalist.tecmeli.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alcalist.tecmeli.core.network.AppError

@Composable
fun ErrorState(
    error: AppError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (title, description, icon) = when (error) {
        is AppError.Network -> Triple(
            "Sin conexión",
            "Parece que tienes problemas con tu internet. Revisa tu conexión y vuelve a intentarlo.",
            Icons.Default.Warning
        )
        is AppError.Timeout -> Triple(
            "Tiempo agotado",
            "La solicitud está tardando demasiado. Por favor, intenta de nuevo.",
            Icons.Default.Refresh
        )
        is AppError.Server -> Triple(
            "Error del servidor",
            "Estamos teniendo problemas técnicos (Error ${error.code}). Intenta más tarde.",
            Icons.Default.Info
        )
        else -> Triple(
            "Ups, algo salió mal",
            "Ocurrió un error inesperado. Por favor, intenta de nuevo.",
            Icons.Default.Info
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error // Corregido: se usa 'tint' en Compose
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Reintentar")
        }
    }
}
