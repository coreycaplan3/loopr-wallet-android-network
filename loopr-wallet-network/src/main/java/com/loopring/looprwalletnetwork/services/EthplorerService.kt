package com.loopring.looprwalletnetwork.services

import com.example.arknw229.networkingtests.*
import com.google.gson.GsonBuilder
import com.loopring.looprwalletnetwork.models.etherscan.CoinPriceData
import com.loopring.looprwalletnetwork.models.etherscan.transactions.EthTransactionOperationInfo
import com.loopring.looprwalletnetwork.models.ethplorer.address.EthAddressHistory
import com.loopring.looprwalletnetwork.models.ethplorer.address.EthAddressTransactions
import com.loopring.looprwalletnetwork.models.ethplorer.tokens.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by arknw229 on 2/20/18.
 *
 * Ethplorer API service
 * All calls are to *https://api.ethplorer.io*
 * @author arknw229
 */
@Suppress("unused")
interface EthplorerService {

    /**
     * Get information on an ERC20 token given the token's address
     * Includes price information when available
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-token-info)
     *
     * @param address - address of the ERC20 token contract
     * @return [EthTokenInfo] object with data about the token
     */
    @GET("getTokenInfo/{address}")
    fun getTokenInfo(@Path("address") address: String): Call<EthTokenInfo>

    /**
     * Get information on about an address
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-address-info)
     *
     * @param address: address you're retrieving information on
     * @param token: (optional, leave null if not being used) show information for only the specified token
     * @return [EthAddressInfo] object with data about the address
     */
    @GET("getAddressInfo/{address}?token={token}")
    fun getAddressInfo(@Path("address") address: String, @Path("token") token: String?): Call<EthAddressInfo>

    /**
     * Get information on about a transaction
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-transaction-info)
     *
     * @param txHash: transaction you're retrieving information on
     * @return [EthTransactionInfo] object with data about the transaction
     */
    @GET("getTxInfo/{transactionHash}")
    fun getTxInfo(@Path("transactionHash") txHash: String): Call<EthTransactionInfo>

    /**
     * Get last operations of a token
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-last-token-operations)
     *
     * @param address: ERC20 token address you're retrieving information on
     * @param limit: (optional, leave null if not being used) maximum number of operations [1 - 10, default = 10]
     * @param type: (optional, leave null if not being used) show operations of specified type only
     * @return [EthTokenHistory] object with data about the history of the token
     */
    @GET("getTokenHistory/{address}")
    fun getTokenHistory(@Path("address") address: String, @Query("limit") limit: String?, @Query("type") type: String?): Call<EthTokenHistory>

    /**
     * Get last operations performed by an address
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-last-address-operations)
     *
     * @param address: address you're retrieving information on
     * @param token: (optional, leave null if not being used) show only specified token address operations
     * @param type: (optional, leave null if not being used) show operations of specified type only
     * @param limit: (optional, leave null if not being used) maximum number of operations [1 - 10, default = 10]
     * @return [EthAddressHistory] object with data about the history of the address
     */
    @GET("getAddressHistory/{address}")
    fun getAddressHistory(@Path("address") address: String, @Query("token") token: String?, @Query("type") type: String?, @Query("limit") limit: String?): Call<EthAddressHistory>

    /**
     * Get a list of address transactions
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-last-address-operations)
     *
     * @param address: address you're retrieving information on
     * @param showZeroValues: (optional, leave null if not being used) show transactions with zero ETH value, default = 0
     * @param limit: (optional, leave null if not being used) maximum number of operations [1 - 50, default = 10]
     * @return [EthAddressTransactions] object with data about the history of the address
     */
    @GET("getAddressTransactions/{address}")
    fun getAddressTransactions(@Path("address") address: String, @Query("showZeroValues") showZeroValues: String?, @Query("limit") limit: String?): Call<EthAddressTransactions>

    /**
     * Get the top 50 tokens by capitalization
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-top)
     *
     * @param criteria: (optional, leave null if not being used) sort tokens by criteria trade - by trade volume, cap - by capitalization, count - by operations, default = trade]
     * @param limit: (optional, leave null if not being used) maximum number of operations [1 - 50, default = 50]
     * @return [EthTopTokens] object with data about the history of the address
     */
    @GET("getTop/")
    fun getTop(@Query("criteria") criteria: String?, @Query("limit") limit: String?): Call<EthTopTokens>

    /**
     * Shows top 50 of the most active tokens for the last 30 days period
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-top-tokens)
     *
     * @param period: (optional, leave null if not being used) show tokens for specified days period only [optional, 30 days if not set, max. is 30 days for free API key]
     * @param limit: (optional, leave null if not being used) maximum number of operations [1 - 50, default = 50]
     * @return [EthTopTokenActivity] object with data about the history of the address
     */
    @GET("getTopTokens/")
    fun getTopTokens(@Query("period") period: String?, @Query("limit") limit: String?): Call<EthTopTokenActivity>

    /**
     * Shows grouped information for an ERC20 token at the given address
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-grouped-token-history)
     *
     * @param address: address you're retrieving information on
     * @param period: (optional, leave null if not being used) show operations of specified days number only [optional, 30 days if not set, max. is 90 days]
     * @return [EthTokenHistoryGrouped] object with data about the history of the address
     */
    @GET("getTokenHistoryGrouped/{address}")
    fun getTokenHistoryGrouped(@Path("address") address: String, @Query("period") period: String?): Call<EthTokenHistoryGrouped>

    /**
     * Get grouped price history for the token at the given address
     * More information in the docs [here](https://github.com/EverexIO/Ethplorer/wiki/Ethplorer-API#get-grouped-token-price-history)
     *
     * @param address: address you're retrieving information on
     * @param period: (optional, leave null if not being used) show price history of specified days number only [optional, 365 days if not set]
     * @return [EthTokenPriceHistoryGrouped] object with data about the history of the address
     */
    @GET("getTokenPriceHistoryGrouped/{address}")
    fun getTokenPriceHistoryGrouped(@Path("address") address: String, @Query("period") period: String?): Call<EthTokenPriceHistoryGrouped>

    companion object {

        var API_KEY = "freekey"

        private const val BASE_URL = "https://api.ethplorer.io/"

        /**
         * Get a Retrofit reference to use for calling the Ethplorer API functions
         *
         * @return [Retrofit] object configured with the necessary custom deserializers and built with the Ethplorer base URL
         */
        fun getService(): EthplorerService {

            val httpClient = OkHttpClient()
            httpClient.interceptors().add(Interceptor {
                val request = it.request()

                val httpUrl = it.request().url().newBuilder()
                        .addQueryParameter("apiKey", API_KEY)
                        .build()

                val newRequest = request.newBuilder().url(httpUrl).build()
                it.proceed(newRequest)
            })

            val gson = GsonBuilder()
                    .registerTypeAdapter(CoinPriceData::class.java, CoinPriceData.CoinPriceDataDeserializer())
                    .registerTypeAdapter(EthTokenInfo::class.java, EthTokenInfo.EthTokenInfoDeserializer())
                    .registerTypeAdapter(EthTransactionOperationInfo::class.java, EthTransactionOperationInfo.EthTransactionOperationInfoDeserializer())
                    .registerTypeAdapter(EthAddressTransactions::class.java, EthAddressTransactions.EthAddressTransactionsDeserializer())
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .create()

            val retrofit = Retrofit.Builder()
                    .baseUrl(EthplorerService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create<EthplorerService>(EthplorerService::class.java)
        }
    }

}
