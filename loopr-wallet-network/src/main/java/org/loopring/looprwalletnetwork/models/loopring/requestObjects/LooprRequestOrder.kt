package org.loopring.looprwalletnetwork.models.loopring.requestObjects

import com.google.gson.annotations.SerializedName
import org.web3j.crypto.Credentials
import java.math.BigInteger
import java.util.*

data class LooprRequestOrder(
        val protocol: String,
        val delegateAddress: String,
        val owner: String,
        val toSell: String,
        val toBuy: String,
        val sellAmt: BigInteger,
        val buyAmt: BigInteger,
        val validSince: Date,
        val validUntil: BigInteger,
        val lrcFeeP: BigInteger,
        val buyNoMoreThanBuyAmt: Boolean,
        val marginSplitPercentage: Int,
        val credentials: Credentials) {

    val tokenS: String = toSell

    var tokenB: String = toBuy

    var amountS: String = sellAmt.toString(16)

    var amountB: String = buyAmt.toString(16)

    var lrcFee: String = lrcFeeP.toString(16)

    var v: Int = 112 //TODO - Find out how to get this from credentials!

    var r: String = credentials.ecKeyPair.sign(ByteArray(0)).r.toString(16)

    var s: String = credentials.ecKeyPair.sign(ByteArray(0)).s.toString(16)


}