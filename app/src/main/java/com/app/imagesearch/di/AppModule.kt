package com.app.imagesearch.di

import android.app.Application
import android.content.Context
import com.app.imagesearch.constants.ServerConstants
import com.app.imagesearch.data.local.db.RoomDb
import com.app.imagesearch.data.local.db.dao.CommentsDao
import com.app.imagesearch.data.remote.api.ApiService
import com.app.imagesearch.util.LiveDataCallAdapterFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideRetrofitInstance():Retrofit{
       return Retrofit.Builder()
                .baseUrl(ServerConstants.BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
               .build()
    }

    @Singleton
    @Provides fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions()
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions)
    }

    @Singleton
    @Provides
    fun provideDatabaseInstance(@ApplicationContext context: Context): RoomDb {
        return RoomDb.getDatabase(context)
    }

    @Singleton
    @Provides fun provideMsgDao(roomDb: RoomDb): CommentsDao {
        return roomDb.commentsDao()
    }
}