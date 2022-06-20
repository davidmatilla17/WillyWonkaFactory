package com.davidmatillacode.wonkasfactory.di

import com.davidmatillacode.wonkasfactory.core.RealmHelper
import com.davidmatillacode.wonkasfactory.core.RetrofitHelper
import com.davidmatillacode.wonkasfactory.data.network.StaffApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitHelper.getRetrofit()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): StaffApiClient {
        return retrofit.create(StaffApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        return RealmHelper.getRealm()
    }

}