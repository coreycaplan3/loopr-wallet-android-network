package com.loopring.looprwalletnetwork.services

import android.util.Log
import com.google.gson.JsonObject
import com.loopring.looprwalletnetwork.models.etherscan.eth.EthSupplyResponse
import com.loopring.looprwalletnetwork.models.loopring.*
import kotlinx.coroutines.experimental.Deferred
import org.web3j.crypto.Credentials
import retrofit2.Call
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

class LoopringService(contractVer: String) {


    val jsonRpcVersion = "2.0"
    var contractVersion = "v1.2"
    val loopringContractAddress = "0xEF68e7C694F40c8202821eDF525dE3782458639f"
    val id = "64"

    fun init(contractVersion: String) {
        this.contractVersion = contractVersion
    }

    /**
     * Get balances of each token held by an account
     * @param owner - the owner of the tokens
     */
    fun getBalances(owner: String): Deferred<LooprBalance> {
        val service = LoopringServiceInternal.getService()
        var jsonParams = JsonObject()
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("contractVersion", contractVersion)
        return service.getBalances(this.jsonRpcVersion,"loopring_getBalance", jsonParams.toString(),this.id)
    }

    /**
     * Submit an order to the network
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
     * @param v - ECDSA signature parameter v
     * @param r - ECDSA signature parameter r
     * @param s - ECDSA signature parameter s
     *
     */
    fun submitOrder(owner: String, toSell: String, toBuy: String, sellAmt: BigInteger,
                    buyAmt: BigInteger, validSince: Date, validUntil: BigInteger,
                    lrcFee: BigInteger, buyNoMoreThanBuyAmt: Boolean, marginSplitPercentage: Integer,
                    credentials: Credentials): Deferred<LooprBalance> {
        val service = LoopringServiceInternal.getService()
        var jsonParams = JsonObject() //TODO - it may take this as array of objects, find out through testing
        jsonParams.addProperty("protocol", loopringContractAddress)
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("tokenS", toSell)
        jsonParams.addProperty("tokenB", toBuy)
        jsonParams.addProperty("amountS", sellAmt.toString())
        jsonParams.addProperty("amountB", buyAmt.toString())
        jsonParams.addProperty("validSince", validSince.getTime())
        jsonParams.addProperty("validUntil", validUntil.toString())
        jsonParams.addProperty("lrcFee",lrcFee.toString())
        jsonParams.addProperty("buyNoMoreThanBuyAmt",buyNoMoreThanBuyAmt.toString())
        //TODO - get transaciton hash and use code below to get the r and s. Also find out how to get the v
        /*var signedTransaction = credentials.ecKeyPair.sign(ByteArray(0))
        signedTransaction.r
        signedTransaction.s*/
        jsonParams.addProperty("v","")
        jsonParams.addProperty("r","")
        jsonParams.addProperty("s","")
        return service.getBalances(this.jsonRpcVersion,"loopring_submitOrder", jsonParams.toString(),this.id)
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
                    market: String, side: String, pageIndex: Integer,
                    pageSize: Integer): Deferred<LooprOrderList> {
        val service = LoopringServiceInternal.getService()
        var jsonParams = JsonObject() //TODO - it may take this as array of objects, find out through testing
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("orderHash", orderHash)
        jsonParams.addProperty("status", status)
        jsonParams.addProperty("contractVersion", contractVersion)
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
        var jsonParams = JsonObject()
        jsonParams.addProperty("market", market)
        jsonParams.addProperty("contractVersion", contractVersion)
        jsonParams.addProperty("length", length)

        return service.getDepth(this.jsonRpcVersion,"loopring_getDepth", jsonParams.toString(),this.id)
    }

    /**
     * Get all market 24hr merged tickers info from loopring relay
     * @param market - The market pair, example - "LRC-WETH"
     *
     */
    fun getTicker(market: String): Deferred<LooprTickerList> {
        val service = LoopringServiceInternal.getService()
        var jsonParams = JsonObject()
        jsonParams.addProperty("market", market)

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
        var jsonParams = JsonObject()
        jsonParams.addProperty("market", market)

        return service.getTickers(this.jsonRpcVersion,"loopring_getTickers", jsonParams.toString(),this.id)
    }

    /**
     * Get order fill history. This history consists of OrderFilled events
     * @param market - The market of the order. Example input - "LRC-WETH"
     * @param contractVersion - the loopring contract version you selected. Example input - "v1.2"
     * @param owner - The address, if is null, will query all orders. Example input - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     * @param orderHash - The order hash. Example input - "0xee0b482d9b704070c970df1e69297392a8bb73f4ed91213ae5c1725d4d1923fd"
     * @param ringHash - The order fill related ring's hash - "0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5"
     * @param pageIndex - The page want to query. Example input - 2
     * @param pageSize - The size per page. Example input - 20
     *
     */
    fun getFills(market: String, owner: String, orderHash: String, ringHash: String,
                   pageIndex: Integer, pageSize: Integer): Deferred<LooprFillsList> {
        val service = LoopringServiceInternal.getService()
        var jsonParams = JsonObject()
        jsonParams.addProperty("market", market)
        jsonParams.addProperty("contractVersion", contractVersion)
        jsonParams.addProperty("owner", owner)
        jsonParams.addProperty("orderHash", orderHash)
        jsonParams.addProperty("ringHash", ringHash)
        jsonParams.addProperty("pageIndex", pageIndex)
        jsonParams.addProperty("pageSize", pageSize)

        return service.getFills(this.jsonRpcVersion,"loopring_getFills", jsonParams.toString(),this.id)
    }


}