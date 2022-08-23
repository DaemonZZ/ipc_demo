package com.thangdn6.aidlclient.appModule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val TAG = "ThangDN6 - AppModule"

    @Singleton
    @Provides
    fun provideIOScope() = CoroutineScope(Dispatchers.IO)

}