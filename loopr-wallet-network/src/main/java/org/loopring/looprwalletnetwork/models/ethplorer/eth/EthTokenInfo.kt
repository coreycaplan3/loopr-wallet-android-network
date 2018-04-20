package org.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import org.loopring.looprwalletnetwork.utilities.ifNotNullOrEmpty
import org.loopring.looprwalletnetwork.utilities.parseBigDecimal
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal
import java.util.*

/**
 * Created by arknw229 on 3/11/18.
 *
 * Ethplorer API
 *
 * This is a commonly used object for the Ethplorer API
 *
 * The fields change depending on which API call is being made, often many are null
 *
 * ```
 * {
 *   address:       # token address,
 *   totalSupply:   # total token supply,
 *   name:          # token name,
 *   symbol:        # token symbol,
 *   decimals:      # number of significant digits,
 *   price: {       # token price (false, if not available)
 *     rate:        # current rate
 *     currency:    # token price currency (USD)
 *     diff:        # 24 hour rate difference (in percent)
 *     ts:          # last rate update timestamp
 *   },
 *   owner:         # token owner address,
 *   countOps:      # total count of token operations
 *   totalIn:       # total amount of incoming tokens
 *   totalOut:      # total amount of outgoing tokens
 *   holdersCount:  # total number of token holders
 *   issuancesCount:# total count of token issuances
 * }
 * ```
 *
 * @author arknw229
 */
open class EthTokenInfo : RealmObject() {

    /**
     * Token address
     */
    var address: String? = null

    /**
     * Name of the token
     */
    var name: String? = null

    /**
     * Number of decimals the token has
     */
    var decimals: Int? = null

    /**
     * Symbol for the token
     */
    var symbol: String? = null

    /**
     * Total supply of the token
     */
    var totalSupply: BigDecimal?
        get() {
            return mTotalSupply?.let { BigDecimal(it) }
        }
        set(value) {
            mTotalSupply = value?.toPlainString()
        }

    @SerializedName("totalSupply")
    private var mTotalSupply: String? = null

    /**
     * Owner of the contract that created the token
     */
    var owner: String? = null

    /**
     * Number of token transfers that have been done with the token
     */
    var transfersCount: Long? = null

    /**
     * Timestamp of the last update
     */
    var lastUpdated: Date? = null

    /**
     * Total amount of incoming tokens
     */
    var totalIn: BigDecimal?
        get() {
            return mTotalIn?.let { BigDecimal(it) }
        }
        set(value) {
            mTotalIn = value?.toPlainString()
        }

    @SerializedName("totalIn")
    var mTotalIn: String? = null

    /**
     * Total amount of outgoing tokens
     */
    var totalOut: BigDecimal?
        get() {
            return mTotalOut?.let { BigDecimal(it) }
        }
        set(value) {
            mTotalOut = value?.toPlainString()
        }

    @SerializedName("totalOut")
    var mTotalOut: String? = null

    /**
     * Issuance count for the token
     */
    var issuancesCount: Long? = null

    /**
     * Holders count for the token
     */
    var holdersCount: Long? = null

    /**
     * Link to an image for the logo of a token (png)
     */
    var image: String? = null

    /**
     * Description of the token
     */
    var description: String? = null

    /**
     * [CoinPriceData] object with price data on the token (if available)
     */
    var price: CoinPriceData? = null

    /**
     * Total count of token operations
     */
    var countOps: Long? = null

    /**
     * Volume in the current day for the token
     */
    var volume1dCurrent: BigDecimal?
        get() {
            return mVolume1dCurrent?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume1dCurrent = value?.toPlainString()
        }

    @SerializedName("volume-1d-current")
    var mVolume1dCurrent: String? = null

    /**
     * Volume in the previous day for the token
     */
    var volume1dPrevious: BigDecimal?
        get() {
            return mVolume1dPrevious?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume1dPrevious = value?.toPlainString()
        }

    @SerializedName("volume-1d-previous")
    var mVolume1dPrevious: String? = null

    /**
     * Capitalization for the token from the current day
     */
    var cap1dCurrent: BigDecimal?
        get() {
            return mCap1dCurrent?.let { BigDecimal(it) }
        }
        set(value) {
            mCap1dCurrent = value?.toPlainString()
        }

    @SerializedName("cap-1d-current")
    var mCap1dCurrent: String? = null

    /**
     * Capitalization for the token from the previous day
     */
    var cap1dPrevious: BigDecimal?
        get() {
            return mCap1dPrevious?.let { BigDecimal(it) }
        }
        set(value) {
            mCap1dPrevious = value?.toPlainString()
        }

    @SerializedName("cap-1d-previous")
    var mCap1dPrevious: String? = null

    /**
     * Timestamp of the previous 1 day period data
     */
    @SerializedName("cap-1d-previous-ts")
    var cap1dPreviousTs: Date? = null

    /**
     * Volume of the current 7 day period for the token
     */
    var volume7dCurrent: BigDecimal?
        get() {
            return mVolume7dCurrent?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume7dCurrent = value?.toPlainString()
        }

    @SerializedName("volume-7d-current")
    var mVolume7dCurrent: String? = null

    /**
     * Volume of the previous 7 day period for the token
     */
    var volume7dPrevious: BigDecimal?
        get() {
            return mVolume7dPrevious?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume7dPrevious = value?.toPlainString()
        }

    @SerializedName("volume-7d-previous")
    var mVolume7dPrevious: String? = null

    /**
     * Capitalization for the current 7 day period
     */
    var cap7dCurrent: BigDecimal?
        get() {
            return mCap7dCurrent?.let { BigDecimal(it) }
        }
        set(value) {
            mCap7dCurrent = value?.toPlainString()
        }

    @SerializedName("cap-7d-current")
    var mCap7dCurrent: String? = null

    /**
     * Capitalization for the previous 7 day period
     */
    var cap7dPrevious: BigDecimal?
        get() {
            return mCap7dPrevious?.let { BigDecimal(it) }
        }
        set(value) {
            mCap7dPrevious = value?.toPlainString()
        }

    @SerializedName("cap-7d-previous")
    var mCap7dPrevious: String? = null

    /**
     * Timestamp for the previous 7 day period data
     */
    @SerializedName("cap-7d-previous-ts")
    var cap7dPreviousTs: Date? = null

    /**
     * Volume for the current 30 day period
     */
    var volume30dCurrent: BigDecimal?
        get() {
            return mVolume30dCurrent?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume30dCurrent = value?.toPlainString()
        }

    @SerializedName("volume-30d-current")
    var mVolume30dCurrent: String? = null

    /**
     * Volume for the previous 30 day period
     */
    var volume30dPrevious: BigDecimal?
        get() {
            return mVolume30dPrevious?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume30dPrevious = value?.toPlainString()
        }

    @SerializedName("volume-30d-previous")
    var mVolume30dPrevious: String? = null

    /**
     * Capitalization for the current 30 day period
     */
    var cap30dCurrent: BigDecimal?
        get() {
            return mCap30dCurrent?.let { BigDecimal(it) }
        }
        set(value) {
            mCap30dCurrent = value?.toPlainString()
        }

    @SerializedName("cap-30d-current")
    var mCap30dCurrent: String? = null

    /**
     * Capitalization for the previous 30 day period
     */
    var cap30dPrevious: BigDecimal?
        get() {
            return mCap30dPrevious?.let { BigDecimal(it) }
        }
        set(value) {
            mCap30dPrevious = value?.toPlainString()
        }

    @SerializedName("cap-30d-previous")
    var mCap30dPrevious: String? = null

    /**
     * Timestamp for the previous 30 day capitalization period
     */
    @SerializedName("cap-30d-previous-ts")
    var cap30dPreviousTs: Date? = null

    /**
     * Deserializer for [EthTokenInfo]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class EthTokenInfoDeserializer : JsonDeserializer<EthTokenInfo> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EthTokenInfo? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val tokenInfo = EthTokenInfo()

                // Check for empty string for no address
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::address) {
                    tokenInfo.address = jsonObj.get(it).asString
                }
                // Check for empty string for no name
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::name) {
                    tokenInfo.name = jsonObj.get(it).asString
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::decimals) {
                    try {
                        tokenInfo.decimals = Integer.parseInt(jsonObj.get("decimals").asString)
                    } catch (e: UnsupportedOperationException) {
                        tokenInfo.decimals = null
                    }
                }
                // Check that the symbol is 7 or fewer character, if it's longer cut it to 7
                // Check for empty string for no symbol
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::symbol) {
                    var sym = jsonObj.get(it).asString
                    if (sym.length > 7) {
                        sym = sym.substring(0, 8)
                    }
                    tokenInfo.symbol = sym
                }

                jsonObj.ifNotNullOrEmpty(EthTokenInfo::totalSupply) {
                    tokenInfo.totalSupply = parseBigDecimal(jsonObj, it)
                }

                jsonObj.ifNotNullOrEmpty(EthTokenInfo::owner) {
                    // Check for 0x for no owner
                    if (jsonObj.get(it).asString != "0x") {
                        tokenInfo.owner = jsonObj.get(it).asString
                    }
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::transfersCount) {
                    tokenInfo.transfersCount = jsonObj.get(it).asString.toLongOrNull()
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::lastUpdated) {
                    tokenInfo.lastUpdated = Date(jsonObj.get(it).asString.toLong())
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::totalIn) {
                    tokenInfo.totalIn = parseBigDecimal(jsonObj, it)
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::totalOut) {
                    tokenInfo.totalOut = parseBigDecimal(jsonObj, it)
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::issuancesCount) {
                    tokenInfo.issuancesCount = jsonObj.get(it).asString.toLongOrNull()
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::holdersCount) {
                    tokenInfo.holdersCount = jsonObj.get(it).asString.toLongOrNull()
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::image) {
                    tokenInfo.image = jsonObj.get(it).asString
                }
                // Check for empty string for no description
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::description) {
                    tokenInfo.description = jsonObj.get(it).asString
                }
                if (jsonObj.get("price") != null && !jsonObj.get("price").isJsonNull && !jsonObj.get("price").isJsonPrimitive) {
                    tokenInfo.price = context.deserialize(jsonObj.get("price"), CoinPriceData::class.java)
                }
                jsonObj.ifNotNullOrEmpty(EthTokenInfo::countOps) {
                    tokenInfo.countOps = jsonObj.get(it).asString.toLongOrNull()
                }

                if (jsonObj.get("volume-1d-current") != null && !jsonObj.get("volume-1d-current").isJsonNull) {
                    tokenInfo.volume1dCurrent = parseBigDecimal(jsonObj, "volume-1d-current")
                }

                if (jsonObj.get("volume-1d-previous") != null && !jsonObj.get("volume-1d-previous").isJsonNull) {
                    tokenInfo.volume1dPrevious = parseBigDecimal(jsonObj, "volume-1d-previous")
                }

                if (jsonObj.get("cap-1d-current") != null && !jsonObj.get("cap-1d-current").isJsonNull) {
                    tokenInfo.cap1dCurrent = parseBigDecimal(jsonObj, "cap-1d-current")
                }

                if (jsonObj.get("cap-1d-previous") != null && !jsonObj.get("cap-1d-previous").isJsonNull) {
                    tokenInfo.cap1dPrevious = parseBigDecimal(jsonObj, "cap-1d-previous")
                }

                if (jsonObj.get("cap-1d-previous-ts") != null && !jsonObj.get("cap-1d-previous-ts").isJsonNull) {
                    tokenInfo.cap1dPreviousTs = Date(jsonObj.get("cap-1d-previous-ts").asString.toLong())
                }

                if (jsonObj.get("volume-7d-current") != null && !jsonObj.get("volume-7d-current").isJsonNull) {
                    tokenInfo.volume7dCurrent = parseBigDecimal(jsonObj, "volume-7d-current")
                }

                if (jsonObj.get("volume-7d-previous") != null && !jsonObj.get("volume-7d-previous").isJsonNull) {
                    tokenInfo.volume7dPrevious = parseBigDecimal(jsonObj, "volume-7d-previous")
                }

                if (jsonObj.get("cap-7d-current") != null && !jsonObj.get("cap-7d-current").isJsonNull) {
                    tokenInfo.cap7dCurrent = parseBigDecimal(jsonObj, "cap-7d-current")
                }

                if (jsonObj.get("cap-7d-previous") != null && !jsonObj.get("cap-7d-previous").isJsonNull) {
                    tokenInfo.cap7dPrevious = parseBigDecimal(jsonObj, "cap-7d-previous")
                }

                if (jsonObj.get("cap-7d-previous-ts") != null && !jsonObj.get("cap-7d-previous-ts").isJsonNull) {
                    tokenInfo.cap7dPreviousTs = Date(jsonObj.get("cap-7d-previous-ts").asString.toLong())
                }

                if (jsonObj.get("volume-30d-current") != null && !jsonObj.get("volume-30d-current").isJsonNull) {
                    tokenInfo.volume30dCurrent = parseBigDecimal(jsonObj, "volume-30d-current")
                }

                if (jsonObj.get("volume-30d-previous") != null && !jsonObj.get("volume-30d-previous").isJsonNull) {
                    tokenInfo.volume30dPrevious = parseBigDecimal(jsonObj, "volume-30d-previous")
                }

                if (jsonObj.get("cap-30d-current") != null && !jsonObj.get("cap-30d-current").isJsonNull) {
                    tokenInfo.cap30dCurrent = parseBigDecimal(jsonObj, "cap-30d-current")
                }

                if (jsonObj.get("cap-30d-previous") != null && !jsonObj.get("cap-30d-previous").isJsonNull) {
                    tokenInfo.cap30dPrevious = parseBigDecimal(jsonObj, "cap-30d-previous")
                }

                if (jsonObj.get("cap-30d-previous-ts") != null && !jsonObj.get("cap-30d-previous-ts").isJsonNull) {
                    tokenInfo.cap30dPreviousTs = Date(jsonObj.get("cap-30d-previous-ts").asString.toLong())
                }

                return tokenInfo
            }
        }

    }
}
