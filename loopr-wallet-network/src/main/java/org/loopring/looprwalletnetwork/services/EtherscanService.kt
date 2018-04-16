package org.loopring.looprwalletnetwork.services

import com.google.gson.GsonBuilder
import org.loopring.looprwalletnetwork.models.etherscan.AbiResponse
import org.loopring.looprwalletnetwork.models.etherscan.address.BalanceListResponse
import org.loopring.looprwalletnetwork.models.etherscan.address.BalanceResponse
import org.loopring.looprwalletnetwork.models.etherscan.eth.EthPriceResponse
import org.loopring.looprwalletnetwork.models.etherscan.eth.EthSupplyResponse
import org.loopring.looprwalletnetwork.models.etherscan.transactions.TransactionResponse
import org.loopring.looprwalletnetwork.models.ethplorer.eth.EthTokenInfo
import org.loopring.looprwalletnetwork.models.ethplorer.transactioninfo.EthAddressTransactions
import org.loopring.looprwalletnetwork.utilities.DateDeserializer
import kotlinx.coroutines.experimental.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


/**
 * Created by arknw229 on 2/20/18.
 *
 * **Etherscan API service**
 *
 * All calls are to *https://api.etherscan.io*
 *
 * @author arknw229
 */
@Suppress("unused")
interface EtherscanService {

    /**
     * Ethereum supply
     *
     * @return [EthSupplyResponse]
     */
    @get:GET("api?module=stats&action=ethsupply")
    val ethSupply: Deferred<EthSupplyResponse>

    /**
     * Ethereum price data
     *
     * @return [EthPriceResponse]
     */
    @get:GET("api?module=stats&action=ethprice")
    val ethPrice: Deferred<EthPriceResponse>

    /**
     * Retrieve the ABI for a contract
     *
     * @param address
     * @return [AbiResponse]
     */
    @GET("api?module=contract&action=getabi")
    fun getAbi(@Query("address") address: String): Deferred<AbiResponse>

    /**
     * Retrieve the balance of a given account
     *
     * @param address
     * @return [BalanceResponse]
     */
    @GET("api?module=account&action=balance&tag=latest")
    fun getBalance(@Query("address") address: String): Deferred<BalanceResponse>

    /**
     * Retrieve the balances for several given account
     *
     * @param addressList - comma separated string of addresses
     * @return [BalanceListResponse]
     */
    @GET("api?module=account&action=balancemulti&tag=latest")
    fun getBalances(@Query("address") addressList: String): Deferred<BalanceListResponse>

    /**
     * Retrieve the transaction list for a given account
     *
     * @param address - comma separated string of addresses
     * @return [TransactionResponse]
     */
    @GET("api?module=account&action=txlist&startblock=0&endblock=99999999&sort=asc")
    fun getTransactions(@Query("address") address: String): Deferred<TransactionResponse>

    companion object {

        @Suppress("MemberVisibilityCanBePrivate")
        var apiKey = "TODO"

        private const val BASE_URL = "https://api.etherscan.io/"

        /**
         * Get a Retrofit reference to use for calling the Etherscan API functions
         *
         * @return [Retrofit] object configured with the necessary custom deserializers and built with the Etherscan base URL
         */
        fun getService(): EtherscanService {

            if (apiKey == "TODO") {
                throw IllegalStateException("You need to set the API key in order to use it")
            }

            val httpClient = OkHttpClient()
            httpClient.interceptors().add(Interceptor {
                val request = it.request()

                val httpUrl = it.request().url().newBuilder()
                        .addQueryParameter("apikey", apiKey)
                        .build()

                val newRequest = request.newBuilder().url(httpUrl).build()
                it.proceed(newRequest)
            })

            val gson = GsonBuilder()
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .registerTypeAdapter(EthTokenInfo::class.java, EthTokenInfo.EthTokenInfoDeserializer())
                    .registerTypeAdapter(EthAddressTransactions::class.java, EthAddressTransactions.EthAddressTransactionsDeserializer())
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .create()

            val retrofit = Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(EtherscanService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create<EtherscanService>(EtherscanService::class.java)
        }
    }
}