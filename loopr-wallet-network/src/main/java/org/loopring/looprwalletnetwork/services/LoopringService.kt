package org.loopring.looprwalletnetwork.services

import kotlinx.coroutines.experimental.Deferred
import okhttp3.HttpUrl
import org.loopring.looprwalletnetwork.BuildConfig
import org.loopring.looprwalletnetwork.models.loopring.requestObjects.*
import org.loopring.looprwalletnetwork.models.loopring.responseObjects.*
import org.web3j.crypto.Credentials
import java.math.BigInteger
import java.util.*

class LoopringService {

    val jsonRpcVersion = "2.0"
    private val loopringContractAddress = "0xEF68e7C694F40c8202821eDF525dE3782458639f"
    val delegateAddress = "0x17233e07c67d086464fD408148c3ABB56245FA64" //TODO - find out a good way to constantly hv e the updated version of this
    val id = 64
    var isMock = false
    var mockUrl: HttpUrl? = null

    /**
     * Get user's balance and token allowance info
     * @param owner - the owner of the tokens
     */
    fun getBalances(owner: String): Deferred<LooprBalance> {
        val service = getLoopringService()
        val request = LooprRequestBalance(owner, delegateAddress)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getBalance", request, /*id*/id)

        return service.getBalances(wrapper)
    }

    /**
     * Submit an order
     * The order is submitted to relay as a JSON object, this JSON will be broadcast into
     * peer-to-peer network for off-chain order-book maintenance and ring-mining.
     * Once mined, the ring will be serialized into a transaction and submitted to Ethereum blockchain
     * @param owner - the owner of the tokens being traded away
     * @param toSell - the token to sell, in the form "LoopringContractService"
     * @param toBuy - the token to buy, in the form "Lrc"
     * @param sellAmt - Maximum amount of [sellAmt] to sell. Takes an integer
     * @param buyAmt - Minimum amount of [toBuy] to buy if all sellAmt sold. Takes an inteer
     * @param validSince - Indicating when this order is created
     * @param validUntil - How long, in seconds, will this order live
     * @param lrcFee - Max amount of LRC to pay for miner. The real amount to pay is proportional to fill amount
     * @param buyNoMoreThanBuyAmt - If true, this order does not accept buying more than [buyAmt]
     * @param marginSplitPercentage - The percentage of savings paid to miner (0 to 100)
     * @param credentials - Credentials associated with the current wallet
     *
     */
    fun submitOrder(owner: String, toSell: String, toBuy: String, sellAmt: BigInteger,
                    buyAmt: BigInteger, validSince: Date, validUntil: BigInteger,
                    lrcFee: BigInteger, buyNoMoreThanBuyAmt: Boolean, marginSplitPercentage: Int,
                    credentials: Credentials): Deferred<LooprOrderResponse> {
        val service = getLoopringService()
        val request = LooprRequestOrder(loopringContractAddress, delegateAddress, owner, toSell,
                                        toBuy, sellAmt, buyAmt, validSince, validUntil, lrcFee,
                                        buyNoMoreThanBuyAmt, marginSplitPercentage, credentials)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_submitOrder", request, id)

        return service.submitOrder(wrapper)
    }

    /**
     * Get loopring order list
     * @param owner - The address, if is null, will query all orders
     * @param orderHash - The order hash
     * @param status - order status enum string. Use
     * [LooprOrderList.ORDER_OPENED] (includes ORDER_NEW and ORDER_PARTIAL), [LooprOrderList.ORDER_NEW],
     * [LooprOrderList.ORDER_PARTIAL], [LooprOrderList.ORDER_FINISHED], [LooprOrderList.ORDER_CANCEL], or [LooprOrderList.ORDER_CUTOFF]
     * @param market - The market of the order.(format is LRC-WETH)
     * @param side - The side of order. only support "buy" and "sell"
     * @param pageIndex - The page you want to query, default is 1
     * @param pageSize - The size per page, default is 50
     *
     */
    fun getOrders(owner: String?, orderHash: String?, status: String,
                    market: String?, side: String?, pageIndex: Int?,
                    pageSize: Int?): Deferred<LooprOrderList> {
        val service = getLoopringService()
        val request = LooprRequestOrderList(owner, orderHash, status, side, delegateAddress, market,
                                            pageIndex, pageSize)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getOrders", request, this.id)

        return service.getOrderList(wrapper)
    }

    /**
     * Get depth and accuracy by token pair
     * @param market - The market pair, example - "LRC-WETH"
     * @param length - The length of the depth data, example - 10
     *
     */
    fun getDepth(market: String, length: Int): Deferred<LooprDepth> {
        val service = getLoopringService()
        val request = LooprRequestDepth(market, delegateAddress, length)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getDepth", request, this.id)

        return service.getDepth(wrapper)
    }

    /**
     * Get loopring 24hr merged tickers info from loopring relay
     * No params
     *
     */
    fun getTicker(): Deferred<LooprTickerList> {
        val service = getLoopringService()
        val request = LooprRequestEmpty()
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getTicker", request, this.id)

        return service.getTicker(wrapper)
    }

    /**
     * Get all market 24hr merged tickers info from loopring relay
     * @param market - The market pair, example - "LRC-WETH"
     * //TODO - find out how this is any different from getTicker
     *
     */
    fun getTickers(market: String): Deferred<LooprTickerExchangeList> {
        val service = getLoopringService()
        val request = LooprRequestTickers(market)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getTickers", request, this.id)

        return service.getTickers(wrapper)
    }

    /**
     * Get order fill history. This history consists of OrderFilled events
     * @param market - The market of the order. Example input - "LRC-WETH"
     * @param owner - The address, if is null, will query all orders. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * @param orderHash - The order hash. Example input - "0xee0b482d9b704070c970df1e69297392a8bb73f4ed91213ae5c1725d4d1923fd"
     * @param ringHash - The order fill related ring's hash - "0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5"
     * @param pageIndex - The page want to query. Example input - 2
     * @param pageSize - The size per page. Example input - 20
     *
     */
    fun getFills(market: String, owner: String, orderHash: String, ringHash: String,
                   pageIndex: Int, pageSize: Int): Deferred<LooprFillsList> {
        val service = getLoopringService()
        val request = LooprRequestFills(market, owner, delegateAddress, orderHash, ringHash, pageIndex, pageSize)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getFills", request, this.id)

        return service.getFills(wrapper)
    }

    /**
     * Get trend info per market
     * Will return the latest 100 (or possibly fewer) records in the selected interval.
     * For example will return the last 100 hours of data if 1Hr selected, with each data point being one hour
     * @param market - The market type. Example input - "LRC-WETH"
     * @param interval - The interval. Example inputs - "1Hr", "2Hr", "4Hr", "1Day", "1Week"
     *
     */
    fun getTrend(market: String, interval: String): Deferred<LooprTrendList> {
        val service = getLoopringService()
        val request = LooprRequestTrend(market, interval)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getTrend", request, this.id)

        return service.getTrend(wrapper)
    }

    /**
     * Get all mined rings
     * @param ringHash - The ring hash, if is null, will query all rings. Example input - "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238"
     * @param pageIndex - The page want to query. Example input - 2
     * @param pageSize - The size per page. Example input - 20
     *
     */
    fun getRingMined(ringHash: String, pageIndex: Int, pageSize: Int): Deferred<LooprMinedRingList> {
        val service = getLoopringService()
        val request = LooprRequestRingMined(ringHash, delegateAddress, pageIndex, pageSize)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getRingMined", request, this.id)

        return service.getRingMined(wrapper)
    }

    /**
     * Get cut off time of the address
     * @param address - The address. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * @param blockNumber - "earliest", "latest" or "pending", default is "latest". Use [LooprCutoff.BLOCK_EARLIEST], [LooprCutoff.BLOCK_LATEST], or [LooprCutoff.BLOCK_PENDING]
     *
     */
    fun getCutoff(address: String, blockNumber: String): Deferred<LooprCutoff> {
        val service = getLoopringService()
        val request = LooprRequestCutoff(address, delegateAddress, blockNumber)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getCutoff", request, this.id)

        return service.getCutoff(wrapper)
    }

    /**
     * Get the USD/CNY/BTC quoted price of tokens
     * @param currency - The base currency want to query, supported types is CNY, USD
     *
     */
    fun getPriceQuote(currency: String): Deferred<LooprPriceQuote> {
        val service = getLoopringService()
        val request = LooprRequestPriceQuote(currency)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getPriceQuote", request, this.id)

        return service.getPriceQuote(wrapper)
    }

    /**
     * Get the total frozen amount of all unfinished orders
     * @param owner - The address. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * @param token - The specific token which you want to get. Example input - "WETH"
     *
     */
    fun getEstimatedAllocatedAllowance(owner: String, token: String): Deferred<LooprEstimatedAllocatedAllowance> {
        val service = getLoopringService()
        val request = LooprRequestEstAllocatedAllowance(owner, token)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getEstimatedAllocatedAllowance", request, this.id)

        return service.getEstimatedAllocatedAllowance(wrapper)
    }

    /**
     * Get the total frozen lrcFee of all unfinished orders
     * @param owner - The address, if is null, will query all orders. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * TODO - check if the two gets in the name are a typo (they come from the API docs)
     */
    fun getFrozenLRCFee(owner: String): Deferred<LooprFrozenLRCFee> {
        val service = getLoopringService()
        val request = LooprRequestFrozenLrcFee(owner)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getFrozenLRCFee", request, this.id)

        return service.getFrozenLRCFee(wrapper)
    }

    /**
     * Get relay supported all market pairs
     * No parameters
     *
     */
    fun getSupportedMarket(): Deferred<LooprMarketPairs> {
        val service = getLoopringService()
        val request = LooprRequestEmpty()
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getSupportedMarket", request, this.id)

        return service.getSupportedMarket(wrapper)
    }

    /**
     * Get relay supported all tokens
     * No parameters
     *
     */
    fun getSupportedTokens(): Deferred<LooprSupportedTokenList> {
        val service = getLoopringService()
        val request = LooprRequestEmpty()
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getSupportedTokens", request, this.id)

        return service.getSupportedTokens(wrapper)
    }

    /**
     * Get user's portfolio info
     * @param owner - The owner address, must be applied.  Example input - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     *
     */
    fun getPortfolio(owner: String): Deferred<LooprPortfolio> {
        val service = getLoopringService()
        val request = LooprRequestPortfolio(owner)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getPortfolio", request, this.id)

        return service.getPortfolio(wrapper)
    }

    /**
     * Get user's latest transactions by owner
     * @param owner - The owner address, must be applied. Example input - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     * @param txHash - The transaction hash. Example input - "0xc7756d5d556383b2f965094464bdff3ebe658f263f552858cc4eff4ed0aeafeb"
     * @param symbol - The token symbol like LRC,WETH. Example input - "RDN"
     * @param status - The transaction status, possible inputs are "pending", "success", and "failed"
     * @param txType - The transaction type, possible inputs are "send", "receive", "enable", and "convert"
     * @param pageIndex - The page want to query, default is 1. Example input - 2
     * @param pageSize - The size per page, default is 10. Example input - 20
     *
     */
    fun getTransactions(owner: String, txHash: String, symbol: String, status: String,
                        txType: String, pageIndex: Int, pageSize: Int): Deferred<LooprTransactionList> {
        val service = getLoopringService()
        val request = LooprRequestTransactions(owner, txHash, symbol, status, txType, pageIndex, pageSize)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_getTransactions", request, this.id)

        return service.getTransactions(wrapper)
    }

    /**
     * Tell the relay the unlocked wallet info
     * @param owner - The owner address, must be applied. Example input - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     *
     */
    fun unlockWallet(owner: String): Deferred<LooprUnlockResponse> {
        val service = getLoopringService()
        val request = LooprRequestUnlockWallet(owner)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_unlockWallet", request, this.id)

        return service.unlockWallet(wrapper)
    }

    /**
     * Wallet should notify relay there was a transaction sending to eth network, then relay will get and save the pending transaction immediately
     * @param txHash - The transaction hash. Example input - "0xf462c63f46a4e1dc87a7256d40c5e2ec8262cd006fe98ac0839d1aae61818f84"
     *
     */
    fun notifyTransactionSubmitted(txHash: String): Deferred<LooprTransactionSubmittedResponse> {
        val service = getLoopringService()
        val request = LooprRequestUnlockWallet(txHash)
        val wrapper = LooprRequestWrapper(this.jsonRpcVersion, "loopring_notifyTransactionSubmitted", request, this.id)

        return service.notifyTransactionSubmitted(wrapper)
    }

    fun setMock(baseUrl: HttpUrl) {
        this.isMock = true
        this.mockUrl = baseUrl
    }

    fun setLive() {
        this.isMock = false
    }

    fun getService(): LoopringService {
        return LoopringService()
    }

    private fun getLoopringService(): LoopringServiceInternal{
        /*when(BuildConfig.BUILD_TYPE) {
            "debug" -> TODO("YOUR DEBUG CODE HERE")
            "release" -> TODO("YOUR RELEASE CODE HERE")
            else -> throw IllegalArgumentException("Invalid build type, found ${BuildConfig.BUILD_TYPE}")
        }*/
    if (this.isMock) return LoopringServiceInternal.getMockService(this.mockUrl!!)
        else return LoopringServiceInternal.getService()
    }

}