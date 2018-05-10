package org.loopring.looprwalletnetwork.services

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.loopring.looprwalletnetwork.models.ethereum.EthBlockNum
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface EthereumService {

    /**
     * Returns the number of most recent block
     *
     * @return [EthBlockNum]
     */
    @FormUrlEncoded
    @POST("/")
    fun getBlockNum(@Field("jsonrpc") jsonRpc: String,
                    @Field("method") method: String,
                    @Field("params") params: String,
                    @Field("id") id: String): Deferred<EthBlockNum>

    companion object {

        @Suppress("MemberVisibilityCanBePrivate")

        private const val BASE_URL = "https://rinkeby.infura.io/Ndg19H81uChnkiZB2EEJ"
        //private const val BASE_URL = "https://mainnet.infura.io/Ndg19H81uChnkiZB2EEJ" //TODO - find out if its "/rpc/v2/" or "/eth" that comes after this. Or nothing at all

        /**
         * Get a Retrofit reference to use for calling the Etherscan API functions
         *
         * @return [Retrofit] object configured with the necessary custom deserializers and built with the Etherscan base URL
         */
        fun getService(): EtherscanService {

            val httpClient = OkHttpClient()
            httpClient.interceptors().add(Interceptor {
                val request = it.request()

                val httpUrl = it.request().url().newBuilder()
                        .build()

                val newRequest = request.newBuilder().url(httpUrl).build()
                it.proceed(newRequest)
            })

            val gson = GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .create()

            val retrofit = Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(EthereumService.BASE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create<EtherscanService>(EtherscanService::class.java)
        }
    }

}