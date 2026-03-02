package com.alcalist.tecmeli.core.di

import com.alcalist.tecmeli.data.repository.ProductRepositoryImpl
import com.alcalist.tecmeli.data.repository.TokenRepositoryImpl
import com.alcalist.tecmeli.domain.repository.ProductRepository
import com.alcalist.tecmeli.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt encargado de la vinculación (binding) de las interfaces de repositorio
 * con sus implementaciones concretas.
 *
 * Utiliza la anotación [@Binds] para inyectar implementaciones en lugar de crearlas manualmente,
 * lo cual es más eficiente en términos de código generado por Dagger.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Vincula la interfaz [TokenRepository] con su implementación [TokenRepositoryImpl].
     */
    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    /**
     * Vincula la interfaz [ProductRepository] con su implementación [ProductRepositoryImpl].
     */
    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
