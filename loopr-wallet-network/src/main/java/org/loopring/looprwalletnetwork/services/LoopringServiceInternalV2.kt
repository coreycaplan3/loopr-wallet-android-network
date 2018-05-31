package org.loopring.looprwalletnetwork.services

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import org.loopring.looprwalletnetwork.utilities.DateDeserializer
import kotlinx.coroutines.experimental.Deferred
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.loopring.looprwalletnetwork.models.loopring.requestObjects.LooprRequestWrapper
import org.loopring.looprwalletnetwork.models.loopring.responseObjects.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import android.net.TrafficStats
import org.loopring.looprwalletnetwork.delegates.SocketFactoryDelegate
import java.net.Socket


/**
 * An *internal* interface used to interact directly with the Loopring JSON RPC and make the API
 * easier to work with.
 *
 * This service is compatible with version 2 of the Loopring JSON RPC
 */
internal interface LoopringServiceInternalV2 {
    /**
     * Wallet balance
     *
     * @return [LooprBalance]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getBalances(@Body balanceRequest: LooprRequestWrapper): Deferred<LooprBalance>

    /**
     * Submit an Order
     *
     * @return [LooprOrderResponse]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun submitOrder(@Body orderRequest: LooprRequestWrapper): Deferred<LooprOrderResponse>

    /**
     * Get loopring order list
     *
     * @return [LooprOrderList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getOrderList(@Body orderListRequest: LooprRequestWrapper): Deferred<LooprOrderList>

    /**
     * Get depth and accuracy by token pair
     *
     * @return [LooprDepth]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getDepth(@Body depthRequest: LooprRequestWrapper): Deferred<LooprDepth>

    /**
     * Get all market 24hr merged tickers info from loopring relay
     *
     * @return [LooprTickerList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getTicker(@Body tickerRequest: LooprRequestWrapper): Deferred<LooprTickerList>

    /**
     * Get all market 24hr merged tickers info from loopring relay
     *
     * @return [LooprTickerList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getTickers(@Body tickerListRequest: LooprRequestWrapper): Deferred<LooprTickerExchangeList>

    /**
     * Get order fill history. This history consists of OrderFilled events
     *
     * @return [LooprFillsList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getFills(@Body fillsRequest: LooprRequestWrapper): Deferred<LooprFillsList>

    /**
     * Get order fill history. This history consists of OrderFilled events
     *
     * @return [LooprTrendList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getTrend(@Body trendRequest: LooprRequestWrapper): Deferred<LooprTrendList>

    /**
     * Get all mined rings
     *
     * @return [LooprMinedRingList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getRingMined(@Body ringMinedRequest: LooprRequestWrapper): Deferred<LooprMinedRingList>

    /**
     * Get cut off time of the address
     *
     * @return [LooprCutoff]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getCutoff(@Body cutoffRequest: LooprRequestWrapper): Deferred<LooprCutoff>

    /**
     * Get the total frozen amount of all unfinished orders
     *
     * @return [LooprEstimatedAllocatedAllowance]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getEstimatedAllocatedAllowance(@Body allowanceRequest: LooprRequestWrapper): Deferred<LooprEstimatedAllocatedAllowance>

    /**
     * Get the total frozen lrcFee of all unfinished orders
     *
     * @return [LooprFrozenLRCFee]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getFrozenLRCFee(@Body lrcFeeRequest: LooprRequestWrapper): Deferred<LooprFrozenLRCFee>

    /**
     * Get the USD/CNY/BTC quoted price of tokens
     *
     * @return [LooprPriceQuote]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getPriceQuote(@Body priceQuoteRequest: LooprRequestWrapper): Deferred<LooprPriceQuote>

    /**
     * Get relay supported all market pairs
     *
     * @return [LooprMarketPairs]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getSupportedMarket(@Body supportedMarketRequest: LooprRequestWrapper): Deferred<LooprMarketPairs>

    /**
     * Get relay supported all tokens
     *
     * @return [LooprSupportedTokenList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getSupportedTokens(@Body supportedTokensRequest: LooprRequestWrapper): Deferred<LooprSupportedTokenList>

    /**
     * Get user's portfolio info
     *
     * @return [LooprPortfolio]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getPortfolio(@Body portfolioRequest: LooprRequestWrapper): Deferred<LooprPortfolio>

    /**
     * Get user's latest transactions by owner
     *
     * @return [LooprTransactionList]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun getTransactions(@Body transactionsRequest: LooprRequestWrapper): Deferred<LooprTransactionList>

    /**
     * Tell the relay the unlocked wallet info
     *
     * @return [LooprUnlockResponse]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun unlockWallet(@Body unlockWalletRequest: LooprRequestWrapper): Deferred<LooprUnlockResponse>

    /**
     * Wallet should notify relay there was a transaction sending to eth network, then relay will get and save the pending transaction immediately
     *
     * @return [LooprTransactionSubmittedResponse]
     */
    @POST("rpc/$RELAY_VERSION/")
    fun notifyTransactionSubmitted(@Body notifyTransactionRequest: LooprRequestWrapper): Deferred<LooprTransactionSubmittedResponse>


    companion object {

        const val RELAY_VERSION = "v2"

        /**
         * Get a Retrofit reference to use for calling the Etherscan API functions
         *
         * @return [Retrofit] object configured with the necessary custom deserializers and built with the Etherscan base URL
         */
        fun getService(baseUrl: HttpUrl): LoopringServiceInternalV2 {

            val socketFactory = object : SocketFactoryDelegate() {
                override fun configureSocket(socket: Socket): Socket {
                    TrafficStats.tagSocket(socket)
                    return socket
                }
            }
            val httpClient = OkHttpClient.Builder()
                    .socketFactory(socketFactory)
                    .build()

            val retrofit = Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create<LoopringServiceInternalV2>(LoopringServiceInternalV2::class.java)
        }

        private val gson
            get() = GsonBuilder()
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .registerTypeAdapter(LooprBalance::class.java, LooprBalance.LooprBalanceDeserializer())
                    .registerTypeAdapter(LooprCutoff::class.java, LooprCutoff.LooprCutoffDeserializer())
                    .registerTypeAdapter(LooprMarketPairs::class.java, LooprMarketPairs.LooprMarketPairsDeserializer())
                    .registerTypeAdapter(LooprFill::class.java, LooprFill.LooprFillDeserializer())
                    .registerTypeAdapter(LooprPortfolio::class.java, LooprPortfolio.LooprPortfolioDeserializer())
                    .registerTypeAdapter(LooprTokenInfo::class.java, LooprTokenInfo.LooprTokenInfoDeserializer())
                    .registerTypeAdapter(LooprDepth::class.java, LooprDepth.LooprDepthDeserializer())
                    .registerTypeAdapter(LooprOrderResponse::class.java, LooprOrderResponse.LooprOrderResponseDeserializer())
                    .registerTypeAdapter(LooprOrder::class.java, LooprOrder.LooprOrderDeserializer())
                    .registerTypeAdapter(LooprOrderList::class.java, LooprOrderList.LooprOrderListDeserializer())
                    .registerTypeAdapter(LooprOrderItem::class.java, LooprOrderItem.LooprOrderItemDeserializer())
                    .registerTypeAdapter(LooprDepthListItem::class.java, LooprDepthListItem.LooprDepthListItemDeserializer())
                    .registerTypeAdapter(LooprTickerList::class.java, LooprTickerList.LooprTickerListDeserializer())
                    .registerTypeAdapter(LooprTicker::class.java, LooprTicker.LooprTickerDeserializer())
                    .registerTypeAdapter(LooprTickerExchangeList::class.java, LooprTickerExchangeList.LooprTickerExchangeListDeserializer())
                    .registerTypeAdapter(LooprFillsList::class.java, LooprFillsList.LooprFillsListDeserializer())
                    .registerTypeAdapter(LooprTrendList::class.java, LooprTrendList.LooprTrendListDeserializer())
                    .registerTypeAdapter(LooprTrend::class.java, LooprTrend.LooprTrendDeserializer())
                    .registerTypeAdapter(LooprMinedRingList::class.java, LooprMinedRingList.LooprMinedRingListDeserializer())
                    .registerTypeAdapter(LooprMinedRing::class.java, LooprMinedRing.LooprMinedRingDeserializer())
                    .registerTypeAdapter(LooprPriceQuote::class.java, LooprPriceQuote.LooprPriceQuoteDeserializer())
                    .registerTypeAdapter(LooprTokenPriceQuote::class.java, LooprTokenPriceQuote.LooprTokenPriceQuoteDeserializer())
                    .registerTypeAdapter(LooprEstimatedAllocatedAllowance::class.java, LooprEstimatedAllocatedAllowance.LooprEstimatedAllocatedAllowanceDeserializer())
                    .registerTypeAdapter(LooprFrozenLRCFee::class.java, LooprFrozenLRCFee.LooprFrozenLRCFeeDeserializer())
                    .registerTypeAdapter(LooprPortfolioToken::class.java, LooprPortfolioToken.LooprPortfolioTokenDeserializer())
                    .registerTypeAdapter(LooprTransactionList::class.java, LooprTransactionList.LooprTransactionListDeserializer())
                    .registerTypeAdapter(LooprTransaction::class.java, LooprTransaction.LooprTransactionDeserializer())
                    .registerTypeAdapter(LooprSupportedToken::class.java, LooprSupportedTokenList.LooprSupportedTokenListDeserializer())
                    .registerTypeAdapter(LooprTransactionSubmittedResponse::class.java, LooprTransactionSubmittedResponse.LooprTransactionSubmittedResponseDeserializer())
                    .registerTypeAdapter(LooprUnlockResponse::class.java, LooprUnlockResponse.LooprUnlockResponseDeserializer())
                    .registerTypeAdapter(LooprSupportedToken::class.java, LooprSupportedToken.LooprSupportedTokenDeserializer())
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .create()


    }

}