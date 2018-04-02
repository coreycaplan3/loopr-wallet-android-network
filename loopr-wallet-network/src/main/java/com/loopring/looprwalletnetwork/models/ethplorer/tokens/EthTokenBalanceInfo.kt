package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import com.google.gson.*
import java.lang.reflect.Type
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/12/18.
 *
 * Ethplorer API
 *
 * Token balance information for a single token
 *
 * ```
 * {
 *   tokenInfo: # token data (same format as token info),
 *   balance:   # token balance (as is, not reduced to a floating point value),
 *   totalIn:   # total incoming token value
 *   totalOut:  # total outgoing token value
 * }
 * ```
 *
 * @author arknw229
 */
class EthTokenBalanceInfo (
        /**
     * Token information object
     */
    var tokenInfo: EthTokenInfo? = null,

        /**
     * Token balance information
     */
    var balance: BigDecimal? = null,

        /**
     * Total tokens of this type that have gone into the given address
     */
    var totalIn: BigDecimal? = null,

        /**
     * Total tokens of this type that have gone out of the given address
     */
    var totalOut: BigDecimal? = null
) {
    /**
     * Deserializer for [EthTokenBalanceInfo]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class EthTokenBalanceInfoDeserializer : JsonDeserializer<EthTokenBalanceInfo> {

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EthTokenBalanceInfo? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val balanceInfo = EthTokenBalanceInfo()
                if (jsonObj.get("tokenInfo") != null && !jsonObj.get("tokenInfo").isJsonNull && !jsonObj.get("tokenInfo").isJsonPrimitive) {
                    val gson = GsonBuilder().registerTypeAdapter(EthTokenInfo::class.java, EthTokenInfo.EthTokenInfoDeserializer()).serializeNulls().create()
                    balanceInfo.tokenInfo = gson.fromJson(jsonObj.get("tokenInfo").asJsonObject, EthTokenInfo::class.java)
                }
                if (jsonObj.get("balance") != null && !jsonObj.get("balance").isJsonNull && jsonObj.get("balance").asString.equals("")) {
                    balanceInfo.balance = BigDecimal(jsonObj.get("balance").asString)//.replace('e', 'E'))
                }
                if (jsonObj.get("totalIn") != null && !jsonObj.get("totalIn").isJsonNull && jsonObj.get("balance").asString.equals("")) {
                    balanceInfo.totalIn = BigDecimal(jsonObj.get("totalIn").asString)//.replace('e', 'E'))
                }
                if (jsonObj.get("totalOut") != null && !jsonObj.get("totalOut").isJsonNull && jsonObj.get("balance").asString.equals("")) {
                    balanceInfo.totalOut = BigDecimal(jsonObj.get("totalOut").asString)//.replace('e', 'E'))
                }

                return balanceInfo
            }
        }
    }
}