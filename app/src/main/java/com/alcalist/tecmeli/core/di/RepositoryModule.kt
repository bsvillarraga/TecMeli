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

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
