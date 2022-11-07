package com.mendelin.catpediahilt.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mendelin.catpediahilt.BuildConfig
import com.mendelin.catpediahilt.data.local.OfflineRepository
import com.mendelin.catpediahilt.data.local.dao.CatsDao
import com.mendelin.catpediahilt.data.local.dao.CatsDatabase
import com.mendelin.catpediahilt.data.remote.CatpediaApi
import com.mendelin.catpediahilt.data.remote.CatpediaRepository
import com.mendelin.catpediahilt.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request()
                .newBuilder()
                .addHeader(BuildConfig.API_HEADER, BuildConfig.API_KEY)

            chain.proceed(builder.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            addInterceptor(interceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideGsonClient(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideCatpediaApi(retrofit: Retrofit): CatpediaApi {
        return retrofit.create(CatpediaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCatpediaRepository(api: CatpediaApi): CatpediaRepository {
        return CatpediaRepository(api)
    }

    @Provides
    @Singleton
    fun provideCatsDao(@ApplicationContext context: Context): CatsDao {
        return CatsDatabase.getDatabase(context)
            .catsDao()
    }

    @Provides
    @Singleton
    fun provideOfflineRepository(dao: CatsDao): OfflineRepository {
        return OfflineRepository(dao)
    }

    @Provides
    @Singleton
    fun provideBreedInfoUseCase(repository: CatpediaRepository): BreedInfoUseCase {
        return BreedInfoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideBreedsListUseCase(repository: CatpediaRepository): BreedsListUseCase {
        return BreedsListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateBreedUseCase(offlineRepository: OfflineRepository): CreateBreedUseCase {
        return CreateBreedUseCase(offlineRepository)
    }

    @Provides
    @Singleton
    fun provideGetBreedDetailsUseCase(offlineRepository: OfflineRepository): GetBreedDetailsUseCase {
        return GetBreedDetailsUseCase(offlineRepository)
    }

    @Provides
    @Singleton
    fun provideGetBreedsUseCasee(offlineRepository: OfflineRepository): GetBreedsUseCase {
        return GetBreedsUseCase(offlineRepository)
    }
}