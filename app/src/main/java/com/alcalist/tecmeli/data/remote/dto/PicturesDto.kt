package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Objeto de Transferencia de Datos (DTO) que representa una imagen del producto.
 *
 * @property id Identificador único de la imagen provisto por el servidor.
 * @property url Dirección URL pública de la imagen.
 */
data class PicturesDto(
    @SerializedName("id") var id: String,
    @SerializedName("url") var url: String
)
