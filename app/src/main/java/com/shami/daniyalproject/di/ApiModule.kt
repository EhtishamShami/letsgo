package com.shami.daniyalproject.di

import android.util.Log
import com.shami.daniyalproject.BuildConfig
import com.shami.daniyalproject.utils.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Shami on 3/23/2018.
 */

@Module
public class ApiModule
{
    /*
Header Initialization
*/
    @Provides
    @Singleton
    fun getHeaders(): HashMap<String, String> {
        val params = HashMap<String, String>()
        params.put("Content-Type", "application/json")
        //params.put("enctype","multipart/form-data")
        return params
    }


    @Provides
    @Singleton
    protected fun provideOkHttpClientDefault(interceptor: HttpLoggingInterceptor, headers:HashMap<String,String>, timeout:Int): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
        okBuilder.addInterceptor(interceptor)
        okBuilder.addInterceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()

            if (headers != null && headers.size > 0) {
                for ((key, value) in headers) {
                    builder.addHeader(key, value)
                    Log.e(key, value)
                }
            }
            chain.proceed(builder.build())
        }

//        val timeout = getTimeOut()
        okBuilder.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
        okBuilder.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
        okBuilder.writeTimeout(timeout.toLong(), TimeUnit.SECONDS)

        return okBuilder.build()
    }

    @Provides
    @Singleton
    protected fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }




    @Provides
    fun getTimeOut(): Int {
        return 30
    }



    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit
    {
        val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.baseURL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return builder
    }

    @Named(Constant.SENDPKAPI)
    @Provides
    @Singleton
    fun provideRetrofitBuilderForSms(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl("http://sendpk.com/api/sms.php/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return builder
    }

}