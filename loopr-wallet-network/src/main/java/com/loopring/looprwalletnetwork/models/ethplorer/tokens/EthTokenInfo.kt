package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import android.util.Log
import com.google.gson.*

import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.models.etherscan.CoinPriceData
import java.lang.reflect.Type

import java.math.BigDecimal

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
 *   address:        # token address,
 *   totalSupply:    # total token supply,
 *   name:           # token name,
 *   symbol:         # token symbol,
 *   decimals:       # number of significant digits,
 *   price: {        # token price (false, if not available)
 *     rate:       # current rate
 *     currency:   # token price currency (USD)
 *     diff:       # 24 hour rate difference (in percent)
 *     ts:         # last rate update timestamp
 *   },
 *   owner:          # token owner address,
 *   countOps:       # total count of token operations
 *   totalIn:        # total amount of incoming tokens
 *   totalOut:       # total amount of outgoing tokens
 *   holdersCount:   # total numnber of token holders
 *   issuancesCount: # total count of token issuances
 * }
 * ```
 *
 * @author arknw229
 */
class EthTokenInfo(
        /**
         * Token address
         */
        var address: String? = null,

        /**
         * Name of the token
         */
        var name: String? = null,

        /**
         * Number of decimals the token has
         */
        var decimals: Int? = null,

        /**
         * Symbol for the token
         */
        var symbol: String? = null,

        /**
         * Total supply of the token
         */
        var totalSupply: BigDecimal? = null,

        /**
         * Owner of the contract that created the token
         */
        var owner: String? = null,

        /**
         * Number of token transfers that have been done with the token
         */
        var transfersCount: Long? = null,

        /**
         * Timestamp of the last update
         */
        var lastUpdated: Long? = null,

        /**
         * Total amount of incoming tokens
         */
        var totalIn: BigDecimal? = null,

        /**
         * Total amount of outgoing tokens
         */
        var totalOut: BigDecimal? = null,

        /**
         * Issuances count for the token
         */
        var issuancesCount: Long? = null,

        /**
         * Holders count for the token
         */
        var holdersCount: Long? = null,

        /**
         * Link to an image for the logo of a token (png)
         */
        var image: String? = null,

        /**
         * Description of the token
         */
        var description: String? = null,

        /**
         * [CoinPriceData] object with price data on the token (if available)
         */
        var price: CoinPriceData? = null,

        /**
         * Total count of token operations
         */
        var countOps: Long? = null,

        /**
         * Volume in the current day for the token
         */
        @SerializedName("volume-1d-current")
        var volume1dCurrent: BigDecimal? = null,

        /**
         * Volume in the previous day for the token
         */
        @SerializedName("volume-1d-previous")
        var volume1dPrevious: BigDecimal? = null,

        /**
         * Capitalization for the token from the current day
         */
        @SerializedName("cap-1d-current")
        var cap1dCurrent: BigDecimal? = null,

        /**
         * Capitalization for the token from the previous day
         */
        @SerializedName("cap-1d-previous")
        var cap1dPrevious: BigDecimal? = null,

        /**
         * Timestamp of the previous 1 day period data
         */
        @SerializedName("cap-1d-previous-ts")
        var cap1dPreviousTs: BigDecimal? = null,

        /**
         * Volume of the current 7 day period for the token
         */
        @SerializedName("volume-7d-current")
        var volume7dCurrent: BigDecimal? = null,

        /**
         * Volume of the previous 7 day period for the token
         */
        @SerializedName("volume-7d-previous")
        var volume7dPrevious: BigDecimal? = null,

        /**
         * Capitalziation for the current 7 day period
         */
        @SerializedName("cap-7d-current")
        var cap7dCurrent: BigDecimal? = null,

        /**
         * Capitalization for the previous 7 day period
         */
        @SerializedName("cap-7d-previous")
        var cap7dPrevious: BigDecimal? = null,

        /**
         * Timestamp for the previous 7 day period data
         */
        @SerializedName("cap-7d-previous-ts")
        var cap7dPreviousTs: BigDecimal? = null,

        /**
         * Volume for the current 30 day period
         */
        @SerializedName("volume-30d-current")
        var volume30dCurrent: BigDecimal? = null,

        /**
         * Volume for the previous 30 day period
         */
        @SerializedName("volume-30d-previous")
        var volume30dPrevious: BigDecimal? = null,

        /**
         * Capitalization for the current 30 day period
         */
        @SerializedName("cap-30d-current")
        var cap30dCurrent: BigDecimal? = null,

        /**
         * Capitalization for the previous 30 day period
         */
        @SerializedName("cap-30d-previous")
        var cap30dPrevious: BigDecimal? = null,

        /**
         * Timestamp for the previous 30 day capitalization period
         */
        @SerializedName("cap-30d-previous-ts")
        var cap30dPreviousTs: BigDecimal? = null
) {
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
                if (jsonObj.get("address") != null && !jsonObj.get("address").isJsonNull && jsonObj.get("address").asString != "") {
                    tokenInfo.address = jsonObj.get("address").asString
                }
                // Check for empty string for no name
                if (jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull && jsonObj.get("name").asString != "") {
                    tokenInfo.name = jsonObj.get("name").asString
                }
                if (jsonObj.get("decimals") != null && !jsonObj.get("decimals").isJsonNull) {
                    try {
                        tokenInfo.decimals = Integer.parseInt(jsonObj.get("decimals").asString)
                    } catch (e: UnsupportedOperationException) {
                        tokenInfo.decimals = null
                    }
                }
                // Check that the symbol is 7 or fewer character, if it's longer cut it to 7
                // Check for empty string for no symbol
                if (jsonObj.get("symbol") != null && !jsonObj.get("symbol").isJsonNull && jsonObj.get("symbol").asString != "") {
                    var sym = jsonObj.get("symbol").asString
                    if (sym.length > 7) {
                        sym = sym.substring(0, 8)
                    }
                    tokenInfo.symbol = sym
                }
                if (jsonObj.get("totalSupply") != null && !jsonObj.get("totalSupply").isJsonNull) {
                    tokenInfo.totalSupply = parseBigDecimal(jsonObj.get("totalSupply").asString, "totalSupply")
                }
                // Check for 0x for no owner
                if (jsonObj.get("owner") != null && !jsonObj.get("owner").isJsonNull && jsonObj.get("owner").asString != "0x") {
                    tokenInfo.owner = jsonObj.get("owner").asString
                }
                if (jsonObj.get("transfersCount") != null && !jsonObj.get("transfersCount").isJsonNull) {
                    tokenInfo.transfersCount = parseLong(jsonObj.get("transfersCount"), "transfersCount")
                }
                if (jsonObj.get("lastUpdated") != null && !jsonObj.get("lastUpdated").isJsonNull) {
                    tokenInfo.lastUpdated = parseLong(jsonObj.get("lastUpdated"), "lastUpdated")
                }
                if (jsonObj.get("totalIn") != null && !jsonObj.get("totalIn").isJsonNull) {
                    tokenInfo.totalIn = parseBigDecimal(jsonObj.get("totalIn").asString, "totalIn")//.replace('e', 'E'))
                }
                if (jsonObj.get("totalOut") != null && !jsonObj.get("totalOut").isJsonNull) {
                    tokenInfo.totalOut = parseBigDecimal(jsonObj.get("totalOut").asString, "totalOut")//.replace('e', 'E'))
                }
                if (jsonObj.get("issuancesCount") != null && !jsonObj.get("issuancesCount").isJsonNull) {
                    tokenInfo.issuancesCount = parseLong(jsonObj.get("issuancesCount"), "issuancesCount")
                }
                if (jsonObj.get("holdersCount") != null && !jsonObj.get("holdersCount").isJsonNull) {
                    tokenInfo.holdersCount = parseLong(jsonObj.get("holdersCount"), "holdersCount")
                }
                if (jsonObj.get("image") != null && !jsonObj.get("image").isJsonNull) {
                    tokenInfo.image = jsonObj.get("image").asString
                }
                // Check for empty string for no description
                if (jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull && jsonObj.get("description").asString != "") {
                    tokenInfo.description = jsonObj.get("description").asString
                }
                if (jsonObj.get("price") != null && !jsonObj.get("price").isJsonNull && !jsonObj.get("price").isJsonPrimitive) {
                    val gson = GsonBuilder().registerTypeAdapter(CoinPriceData::class.java, CoinPriceData.CoinPriceDataDeserializer()).serializeNulls().create()
                    tokenInfo.price = gson.fromJson(jsonObj.get("price").asJsonObject, CoinPriceData::class.java)
                }
                if (jsonObj.get("countOps") != null && !jsonObj.get("countOps").isJsonNull) {
                    tokenInfo.countOps = parseLong(jsonObj.get("countOps"), "countOps")
                }
                if (jsonObj.get("volume-1d-current") != null && !jsonObj.get("volume-1d-current").isJsonNull) {
                    tokenInfo.volume1dCurrent = parseBigDecimal(jsonObj.get("volume-1d-current").asString, "volume-1d-current")
                }
                if (jsonObj.get("volume-1d-previous") != null && !jsonObj.get("volume-1d-previous").isJsonNull) {
                    tokenInfo.volume1dPrevious = parseBigDecimal(jsonObj.get("volume-1d-previous").asString, "volume-1d-previous")
                }
                if (jsonObj.get("cap-1d-current") != null && !jsonObj.get("cap-1d-current").isJsonNull) {
                    tokenInfo.cap1dCurrent = parseBigDecimal(jsonObj.get("cap-1d-current").asString, "cap-1d-current")
                }
                if (jsonObj.get("cap-1d-previous") != null && !jsonObj.get("cap-1d-previous").isJsonNull) {
                    tokenInfo.cap1dPrevious = parseBigDecimal(jsonObj.get("cap-1d-previous").asString, "cap-1d-previous")
                }
                if (jsonObj.get("cap-1d-previous-ts") != null && !jsonObj.get("cap-1d-previous-ts").isJsonNull) {
                    tokenInfo.cap1dPreviousTs = parseBigDecimal(jsonObj.get("cap-1d-previous-ts").asString, "cap-1d-previous-ts")
                }
                if (jsonObj.get("volume-7d-current") != null && !jsonObj.get("volume-7d-current").isJsonNull) {
                    tokenInfo.volume7dCurrent = parseBigDecimal(jsonObj.get("volume-7d-current").asString, "volume-7d-current")
                }
                if (jsonObj.get("volume-7d-previous") != null && !jsonObj.get("volume-7d-previous").isJsonNull) {
                    tokenInfo.volume7dPrevious = parseBigDecimal(jsonObj.get("volume-7d-previous").asString, "volume-7d-previous")
                }
                if (jsonObj.get("cap-7d-current") != null && !jsonObj.get("cap-7d-current").isJsonNull) {
                    tokenInfo.cap7dCurrent = parseBigDecimal(jsonObj.get("cap-7d-current").asString, "cap-7d-current")
                }
                if (jsonObj.get("cap-7d-previous") != null && !jsonObj.get("cap-7d-previous").isJsonNull) {
                    tokenInfo.cap7dPrevious = parseBigDecimal(jsonObj.get("cap-7d-previous").asString, "cap-7d-previous")
                }
                if (jsonObj.get("cap-7d-previous-ts") != null && !jsonObj.get("cap-7d-previous-ts").isJsonNull) {
                    tokenInfo.cap7dPreviousTs = parseBigDecimal(jsonObj.get("cap-7d-previous-ts").asString, "cap-7d-previous-ts")
                }
                if (jsonObj.get("volume-30d-current") != null && !jsonObj.get("volume-30d-current").isJsonNull) {
                    tokenInfo.volume30dCurrent = parseBigDecimal(jsonObj.get("volume-30d-current").asString, "volume-30d-current")
                }
                if (jsonObj.get("volume-30d-previous") != null && !jsonObj.get("volume-30d-previous").isJsonNull) {
                    tokenInfo.volume30dPrevious = parseBigDecimal(jsonObj.get("volume-30d-previous").asString, "volume-30d-previous")
                }
                if (jsonObj.get("cap-30d-current") != null && !jsonObj.get("cap-30d-current").isJsonNull) {
                    tokenInfo.cap30dCurrent = parseBigDecimal(jsonObj.get("cap-30d-current").asString, "cap-30d-current")
                }
                if (jsonObj.get("cap-30d-previous") != null && !jsonObj.get("cap-30d-previous").isJsonNull) {
                    tokenInfo.cap30dPrevious = parseBigDecimal(jsonObj.get("cap-30d-previous").asString, "cap-30d-previous")
                }
                if (jsonObj.get("cap-30d-previous-ts") != null && !jsonObj.get("cap-30d-previous-ts").isJsonNull) {
                    tokenInfo.cap30dPreviousTs = parseBigDecimal(jsonObj.get("cap-30d-previous-ts").asString, "cap-30d-previous-ts")
                }

                return tokenInfo
            }
        }

        private fun parseLong(value: JsonElement, name: String): Long? {
            try {
                return value.asLong
            } catch (e: UnsupportedOperationException) {
                Log.w("EthTokenInfo", "Warning: json element '$name' could not be parsed as a Long, variable set to null")
                return null
            }
        }

        private fun parseBigDecimal(value: String, name: String): BigDecimal? {
            try {
                return BigDecimal(value)
            } catch (e: NumberFormatException) {
                Log.w("EthTokenInfo", "Warning: json element '$name' could not be parsed as a BigDecimal, variable set to null")
                return null
            }
        }

    }
}
