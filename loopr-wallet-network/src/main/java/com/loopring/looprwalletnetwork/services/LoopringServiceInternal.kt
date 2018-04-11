package com.loopring.looprwalletnetwork.services

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.loopring.looprwalletnetwork.models.loopring.*
import com.loopring.looprwalletnetwork.utilities.DateDeserializer
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

open interface LoopringServiceInternal {
    /**
     * Wallet balance
     *
     * @return [LooprBalance]
     */
    @FormUrlEncoded
    @POST("/")
    fun getBalances(@Field("jsonrpc") jsonRpc: String,
                    @Field("method") method: String,
                    @Field("params") params: String,
                    @Field("id") id: String): Deferred<LooprBalance>

    /**
     * Submit an Order
     *
     * @return [LooprOrderResponse]
     */
    @FormUrlEncoded
    @POST("/")
    fun submitOrder(@Field("jsonrpc") jsonRpc: String,
                    @Field("method") method: String,
                    @Field("params") params: String,
                    @Field("id") id: String): Deferred<LooprOrderResponse>

    /**
     * Get loopring order list
     *
     * @return [LooprOrderList]
     */
    @FormUrlEncoded
    @POST("/")
    fun getOrderList(@Field("jsonrpc") jsonRpc: String,
                     @Field("method") method: String,
                     @Field("params") params: String,
                     @Field("id") id: String): Deferred<LooprOrderList>

    /**
     * Get depth and accuracy by token pair
     *
     * @return [LooprDepth]
     */
    @FormUrlEncoded
    @POST("/")
    fun getDepth(@Field("jsonrpc") jsonRpc: String,
                 @Field("method") method: String,
                 @Field("params") params: String,
                 @Field("id") id: String): Deferred<LooprDepth>

    /**
     * Get all market 24hr merged tickers info from loopring relay
     *
     * @return [LooprTickerList]
     */
    @FormUrlEncoded
    @POST("/")
    fun getTicker(@Field("jsonrpc") jsonRpc: String,
                  @Field("method") method: String,
                  @Field("params") params: String,
                  @Field("id") id: String): Deferred<LooprTickerList>

    /**
     * Get all market 24hr merged tickers info from loopring relay
     *
     * @return [LooprTickerList]
     */
    @FormUrlEncoded
    @POST("/")
    fun getTickers(@Field("jsonrpc") jsonRpc: String,
                   @Field("method") method: String,
                   @Field("params") params: String,
                   @Field("id") id: String): Deferred<LooprTickerExchangeList>

    /**
     * Get order fill history. This history consists of OrderFilled events
     *
     * @return [LooprFillsList]
     */
    @FormUrlEncoded
    @POST("/")
    fun getFills(@Field("jsonrpc") jsonRpc: String,
                 @Field("method") method: String,
                 @Field("params") params: String,
                 @Field("id") id: String): Deferred<LooprFillsList>

    /**
     * Get order fill history. This history consists of OrderFilled events
     *
     * @return [LooprTrendList]
     */
    @FormUrlEncoded
    @POST("/")
    fun getTrend(@Field("jsonrpc") jsonRpc: String,
                 @Field("method") method: String,
                 @Field("params") params: String,
                 @Field("id") id: String): Deferred<LooprTrendList>

    /**
     * Get all mined rings
     *
     * @return [LooprMinedRingList]
     */
    @FormUrlEncoded
    @POST("/")
    fun getRingMined(@Field("jsonrpc") jsonRpc: String,
                 @Field("method") method: String,
                 @Field("params") params: String,
                 @Field("id") id: String): Deferred<LooprMinedRingList>

    /**
     * Get cut off time of the address
     *
     * @return [LooprCutoff]
     */
    @FormUrlEncoded
    @POST("/")
    fun getCutoff(@Field("jsonrpc") jsonRpc: String,
                     @Field("method") method: String,
                     @Field("params") params: String,
                     @Field("id") id: String): Deferred<LooprCutoff>


    companion object {

        @Suppress("MemberVisibilityCanBePrivate")

        private const val BASE_URL = "http://13.112.62.24/rpc/v2/"//"https://relay1.loopring.io/rpc/"

        /**
         * Get a Retrofit reference to use for calling the Etherscan API functions
         *
         * @return [Retrofit] object configured with the necessary custom deserializers and built with the Etherscan base URL
         */
        fun getService(): LoopringServiceInternal {

            val httpClient = OkHttpClient()
            /*httpClient.interceptors().add(Interceptor {
                val request = it.request()

                val httpUrl = it.request().url().newBuilder()
                        .build()

                val newRequest = request.newBuilder().url(httpUrl).build()
                it.proceed(newRequest)
            })*/

            val gson = GsonBuilder()
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .create()

            val retrofit = Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(LoopringServiceInternal.BASE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create<LoopringServiceInternal>(LoopringServiceInternal::class.java)
        }
    }

}