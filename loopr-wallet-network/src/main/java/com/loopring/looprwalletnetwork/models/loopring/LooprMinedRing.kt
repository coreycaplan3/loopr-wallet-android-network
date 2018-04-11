package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

class LooprMinedRing : RealmObject() {

    /**
     * The ring hash
     * Example output - "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238"
     */
    @SerializedName("ringhash")
    var ringhash: String? = null

    /**
     * The fills number in the ring
     * Example output - 3
     */
    @SerializedName("tradeAmount")
    var tradeAmount: Int? = null

    /**
     * The miner that submit match orders
     * Example output - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     */
    @SerializedName("miner")
    var miner: String? = null

    /**
     * The fee recipient address
     * Example output - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     */
    @SerializedName("feeRecepient")
    var feeRecipient: String? = null

    /**
     * The miner that submit match orders
     * Example output - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     */
    @SerializedName("txHash")
    var txHash: String? = null

    /**
     * The number of the block which contains the transaction
     * Example output - 10001
     */
    var blockNumber: BigInteger?
        get() {
            return mBlockNumber?.let { BigInteger(it) }
        }
        set(value) {
            mBlockNumber = value.toString()
        }

    private var mBlockNumber: String? = null

    /**
     * The total lrc fee
     * Example output - "0x101"
     */
    var totalLrcFee: BigInteger?
        get() {
            return mTotalLrcFee?.let { BigInteger(it) }
        }
        set(value) {
            mTotalLrcFee = value.toString()
        }

    private var mTotalLrcFee: String? = null

    /**
     * The ring matched time
     * Example output - 1506114710
     * TODO - check up on this one's name in the json, the docs show it both as 'time' and 'timestamp'
     */
    @SerializedName("timestamp")
    var timestamp: Date? = null

    /**
     * Custom class deserializer
     */
    class LooprMinedRingDeserializer : JsonDeserializer<LooprMinedRing> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprMinedRing? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val minedRing = LooprMinedRing()
                var trendsJsonObject = json.asJsonObject

                trendsJsonObject.get("ringhash")?.let {
                    minedRing.ringhash  = it.asString
                }

                trendsJsonObject.get("tradeAmount")?.let {
                    minedRing.tradeAmount  = it.asInt
                }

                trendsJsonObject.get("miner")?.let {
                    minedRing.miner  = it.asString
                }

                trendsJsonObject.get("feeRecipient")?.let {
                    minedRing.feeRecipient  = it.asString
                }

                trendsJsonObject.get("txHash")?.let {
                    minedRing.txHash  = it.asString
                }

                trendsJsonObject.get("blockNumber")?.let {
                    minedRing.blockNumber  = BigInteger(it.asString)
                }

                trendsJsonObject.get("totalLrcFee")?.let {
                    minedRing.totalLrcFee  = BigInteger(it.asString)
                }

                trendsJsonObject.get("timestamp")?.let {
                    minedRing.timestamp  = Date(it.asLong)
                }

                return minedRing
            }
        }

    }

}