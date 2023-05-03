package com.platform_science.routing_shipments.di

import com.platform_science.routing_shipments.data.repository.UsersRepositoryImpl
import com.platform_science.routing_shipments.domain.repository.ShipmentsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Set dependency injection
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides repository
     *
     * @return Implementation repository
     */
    @Provides
    @Singleton
    fun provideUsersRepository(): ShipmentsRepository {
        return UsersRepositoryImpl()
    }

}