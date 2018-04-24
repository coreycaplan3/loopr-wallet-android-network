package org.loopring.looprwalletnetwork.services

import com.google.gson.JsonObject
import org.loopring.looprwalletnetwork.models.loopring.*
import kotlinx.coroutines.experimental.Deferred
import org.web3j.crypto.Credentials
import java.math.BigInteger
import java.util.*

class LoopringService() {

    private val jsonRpcVersion = "2.0"
    private val loopringContractAddress = "0xEF68e7C694F40c8202821eDF525dE3782458639f"
    private val delegateAddress = "0x5567ee920f7E62274284985D793344351A00142B" //TODO - ask Corey where a good place to externalize this to is
    val id = "64"

    /**
     * Get user's balance and token allowance info
     * @param owner - the owner of the tokens
     */
    fun getBalances(owner: String): Deferred<LooprBalance> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("delegateAddress", delegateAddress)
        return service.getBalances(this.jsonRpcVersion,"loopring_getBalance", jsonParams.toString(),this.id)
    }

    /**
     * Submit an order
     * The order is submitted to relay as a JSON object, this JSON will be broadcast into
     * peer-to-peer network for off-chain order-book maintenance and ring-mining.
     * Once mined, the ring will be serialized into a transaction and submitted to Ethereum blockchain
     * @param owner - the owner of the tokens being traded away
     * @param toSell - the token to sell, in the form "Eth"
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
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject() //TODO - it may take this as array of objects, find out through testing
        jsonParams.addProperty("protocol", loopringContractAddress)
        jsonParams.addProperty("delegateAddress", delegateAddress)
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("tokenS", toSell)
        jsonParams.addProperty("tokenB", toBuy)
        jsonParams.addProperty("amountS", sellAmt.toString())
        jsonParams.addProperty("amountB", buyAmt.toString())
        jsonParams.addProperty("validSince", validSince.time.toString())
        jsonParams.addProperty("validUntil", validUntil.toString())
        jsonParams.addProperty("lrcFee",lrcFee.toString())
        jsonParams.addProperty("buyNoMoreThanBuyAmt",buyNoMoreThanBuyAmt.toString())
        jsonParams.addProperty("marginSplitPercentage",marginSplitPercentage.toString())
        //TODO - get transaciton hash and use code below to get the r and s. Also find out how to get the v
        /*var signedTransaction = credentials.ecKeyPair.sign(ByteArray(0))
        signedTransaction.r
        signedTransaction.s*/
        jsonParams.addProperty("v","")
        jsonParams.addProperty("r","")
        jsonParams.addProperty("s","")
        return service.submitOrder(this.jsonRpcVersion,"loopring_submitOrder", jsonParams.toString(),this.id)
    }

    /**
     * Get loopring order list
     * @param owner - The address, if is null, will query all orders
     * @param orderHash - The order hash
     * @param status - order status enum string.(status collection is : ORDER_OPENED(include ORDER_NEW and ORDER_PARTIAL), ORDER_NEW, ORDER_PARTIAL, ORDER_FINISHED, ORDER_CANCEL, ORDER_CUTOFF)
     * @param market - The market of the order.(format is LRC-WETH)
     * @param side - The side of order. only support "buy" and "sell"
     * @param pageIndex - The page you want to query, default is 1
     * @param pageSize - The size per page, default is 50
     *
     */
    fun getOrders(owner: String, orderHash: String, status: String,
                    market: String, side: String, pageIndex: Int,
                    pageSize: Int): Deferred<LooprOrderList> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject() //TODO - it may take this as array of objects, find out through testing
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("orderHash", orderHash)
        jsonParams.addProperty("status", status)
        jsonParams.addProperty("delegateAddress", delegateAddress)
        jsonParams.addProperty("market", market)
        jsonParams.addProperty("side", side)
        jsonParams.addProperty("pageIndex", pageIndex)
        jsonParams.addProperty("pageSize", pageSize)

        return service.getOrderList(this.jsonRpcVersion,"loopring_getOrderByHash", jsonParams.toString(),this.id)
    }

    /**
     * Get depth and accuracy by token pair
     * @param market - The market pair, example - "LRC-WETH"
     * @param length - The length of the depth data, example - 10
     *
     */
    fun getDepth(market: String, length: String): Deferred<LooprDepth> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("market", market)
        jsonParams.addProperty("delegateAddress", delegateAddress)
        jsonParams.addProperty("length", length)

        return service.getDepth(this.jsonRpcVersion,"loopring_getDepth", jsonParams.toString(),this.id)
    }

    /**
     * Get loopring 24hr merged tickers info from loopring relay
     * No params
     *
     */
    fun getTicker(market: String): Deferred<LooprTickerList> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject() //TODO - docs show an object in a list, find out through testing if that's true

        return service.getTicker(this.jsonRpcVersion,"loopring_getTicker", jsonParams.toString(),this.id)
    }

    /**
     * Get all market 24hr merged tickers info from loopring relay
     * @param market - The market pair, example - "LRC-WETH"
     * //TODO - find out how this is any different from getTicker
     *
     */
    fun getTickers(market: String): Deferred<LooprTickerExchangeList> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("market", market)

        return service.getTickers(this.jsonRpcVersion,"loopring_getTickers", jsonParams.toString(),this.id)
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
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("market", market)
        jsonParams.addProperty("delegateAddress", delegateAddress)
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("orderHash", orderHash)
        jsonParams.addProperty("ringHash", ringHash)
        jsonParams.addProperty("pageIndex", pageIndex)
        jsonParams.addProperty("pageSize", pageSize)

        return service.getFills(this.jsonRpcVersion,"loopring_getFills", jsonParams.toString(),this.id)
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
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("market", market)
        jsonParams.addProperty("interval",  interval)

        return service.getTrend(this.jsonRpcVersion,"loopring_getTrend", jsonParams.toString(),this.id)
    }

    /**
     * Get all mined rings
     * @param ringHash - The ring hash, if is null, will query all rings. Example input - "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238"
     * @param pageIndex - The page want to query. Example input - 2
     * @param pageSize - The size per page. Example input - 20
     *
     */
    fun getRingMined(ringHash: String, pageIndex: Int, pageSize: Int): Deferred<LooprMinedRingList> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("ringHash", ringHash)
        jsonParams.addProperty("delegateAddress", delegateAddress)
        jsonParams.addProperty("pageIndex", pageIndex)
        jsonParams.addProperty("pageSize", pageSize)

        return service.getRingMined(this.jsonRpcVersion,"loopring_getRingMined", jsonParams.toString(),this.id)
    }

    /**
     * Get cut off time of the address
     * @param address - The address. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * @param blockNumber - The page want to query. Example input - 2
     *
     */
    fun getCutoff(address: String, blockNumber: Int): Deferred<LooprCutoff> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("address", address)
        jsonParams.addProperty("delegateAddress", delegateAddress)
        jsonParams.addProperty("blockNumber", blockNumber)

        return service.getCutoff(this.jsonRpcVersion,"loopring_getCutoff", jsonParams.toString(),this.id)
    }

    /**
     * Get the USD/CNY/BTC quoted price of tokens
     * @param currency - The base currency want to query, supported types is CNY, USD
     *
     */
    fun getPriceQuote(currency: String): Deferred<LooprPriceQuote> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("currency", currency)

        return service.getPriceQuote(this.jsonRpcVersion,"loopring_getPriceQuote", jsonParams.toString(),this.id)
    }

    /**
     * Get the total frozen amount of all unfinished orders
     * @param owner - The address. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * @param tokens - The specific token which you want to get. Example input - "WETH"
     *
     */
    fun getEstimatedAllocatedAllowance(owner: String, tokens: String): Deferred<LooprEstimatedAllocatedAllowance> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("tokens", tokens)

        return service.getEstimatedAllocatedAllowance(this.jsonRpcVersion,"loopring_getEstimatedAllocatedAllowance", jsonParams.toString(),this.id)
    }

    /**
     * Get the total frozen lrcFee of all unfinished orders
     * @param owner - The address, if is null, will query all orders. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * TODO - check if the two gets in the name are a typo (they come from the API docs)
     */
    fun getGetFrozenLRCFee(owner: String): Deferred<LooprFrozenLRCFee> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("owner", owner)

        return service.getGetFrozenLRCFee(this.jsonRpcVersion,"loopring_getGetFrozenLRCFee", jsonParams.toString(),this.id)
    }

    /**
     * Get relay supported all market pairs
     * No parameters
     *
     */
    fun getSupportedMarket(): Deferred<LooprMarketPairs> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()

        return service.getSupportedMarket(this.jsonRpcVersion,"loopring_getSupportedMarket", jsonParams.toString(),this.id)
    }

    /**
     * Get relay supported all tokens
     * No parameters
     *
     */
    fun getSupportedTokens(): Deferred<LooprSupportedTokenList> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()

        return service.getSupportedTokens(this.jsonRpcVersion,"loopring_getSupportedTokens", jsonParams.toString(),this.id)
    }

    /**
     * Get user's portfolio info
     * @param owner - The owner address, must be applied.  Example input - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     *
     */
    fun getGetPortfolio(owner: String): Deferred<LooprPortfolio> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("owner", owner)

        return service.getPortfolio(this.jsonRpcVersion,"loopring_getPortfolio", jsonParams.toString(),this.id)
    }

    /**
     * Get user's latest transactions by owner
     * @param owner - The owner address, must be applied. Example input - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     * @param txHash - The transaction hash. Example input - "0xc7756d5d556383b2f965094464bdff3ebe658f263f552858cc4eff4ed0aeafeb"
     * @param pageIndex - The page want to query, default is 1. Example input - 2
     * @param pageSize - The size per page, default is 10. Example input - 20
     *
     */
    fun getTransactions(owner: String, txHash: String, pageIndex: Int, pageSize: Int): Deferred<LooprTransactionList> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("txHash", txHash)
        jsonParams.addProperty("pageIndex", pageIndex)
        jsonParams.addProperty("pageSize", pageSize)

        return service.getTransactions(this.jsonRpcVersion,"loopring_getTransactions", jsonParams.toString(),this.id)
    }

    /**
     * Tell the relay the unlocked wallet info
     * @param owner - The owner address, must be applied. Example input - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     *
     */
    fun unlockWallet(owner: String): Deferred<LooprUnlockResponse> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("owner", owner)

        return service.unlockWallet(this.jsonRpcVersion,"loopring_unlockWallet", jsonParams.toString(),this.id)
    }

    /**
     * Wallet should notify relay there was a transaction sending to eth network, then relay will get and save the pending transaction immediately
     * @param txHash - The transaction hash. Example input - "0xf462c63f46a4e1dc87a7256d40c5e2ec8262cd006fe98ac0839d1aae61818f84"
     *
     */
    fun notifyTransactionSubmitted(txHash: String): Deferred<LooprTransactionSubmittedResponse> {
        val service = LoopringServiceInternal.getService()
        val jsonParams = JsonObject()
        jsonParams.addProperty("txHash", txHash)

        return service.notifyTransactionSubmitted(this.jsonRpcVersion,"loopring_notifyTransactionSubmitted", jsonParams.toString(),this.id)
    }

}