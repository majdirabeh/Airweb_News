package fr.airweb.news.network

import fr.airweb.news.Constants.Companion.BASE_URL
import fr.airweb.news.network.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Majdi RABEH on 05/12/2020.
 * Email m.rabeh.majdi@gmail.com
 */
val NetworkModule = module {
    factory { provideOkHttpClient(get()) }
    factory { provideApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
}

/**
 * Provide Retrofit
 * @param okHttpClient
 */
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()
}

/**
 * Provide OKHttp
 * @param loggingInterceptor
 */
fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.SECONDS)
        .callTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor).build()
}

/**
 * Provide LoggingInterceptor
 */
fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

/**
 * Provide Api
 * @param retrofit
 */
fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)