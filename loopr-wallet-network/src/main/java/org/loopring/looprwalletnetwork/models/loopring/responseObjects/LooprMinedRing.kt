package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.lang.reflect.Type
import java.math.BigInteger
import java.util.*

open class LooprMinedRing : RealmObject() {

    /**
     * The associated id for the ring
     * Example output - 1
     */
    @SerializedName("id")
    var id: Int? = null

    /**
     * The protocol used
     * Example output - "0xb1170dE31c7f72aB62535862C97F5209E356991b"
     */
    @SerializedName("protocol")
    var protocol: String? = null

    /**
     * The transaction delegate address
     * Example output - "0x5567ee920f7E62274284985D793344351A00142B"
     */
    @SerializedName("delegateAddress")
    var delegateAddress: String? = null

    /**
     * The ring hash
     * Example output - "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238"
     */
    @PrimaryKey
    @SerializedName("ringHash")
    var ringHash: String? = null

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
     * The miner that submit match orders
     * Example output - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     */
    @SerializedName("isRinghashReserved")
    var isRinghashReserved: Boolean? = null

    /**
     * The miner that submit match orders
     * Example output - "0x8888f1f195afa192cfee860698584c030f4c9db1"
     */
    @SerializedName("Fork")
    var fork: Boolean? = null

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
            return mTotalLrcFee?.let { BigInteger(it,16) }
        }
        set(value) {
            mTotalLrcFee = value?.toString(16)
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
                val trendsJsonObject = json.asJsonObject

                trendsJsonObject.get("id")?.let {
                    minedRing.id  = it.asInt
                }

                trendsJsonObject.get("protocol")?.let {
                    minedRing.protocol  = it.asString
                }

                trendsJsonObject.get("delegateAddress")?.let {
                    minedRing.delegateAddress  = it.asString
                }

                trendsJsonObject.get("ringHash")?.let {
                    minedRing.ringHash  = it.asString
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

                trendsJsonObject.get("isRinghashReserved")?.let {
                    minedRing.isRinghashReserved  = it.asBoolean
                }

                trendsJsonObject.get("Fork")?.let {
                    minedRing.fork  = it.asBoolean
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