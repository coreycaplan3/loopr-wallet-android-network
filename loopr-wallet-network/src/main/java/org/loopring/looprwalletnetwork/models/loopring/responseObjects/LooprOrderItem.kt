package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger

open class LooprOrderItem : RealmObject() {

    /**
     * The original [LooprOrder] order info when submitting
     */
    @SerializedName("orginalOrder")
    var originalOrder : LooprOrder?  = null

    /**
     * The current order status
     * Example output - "ORDER_CANCEL"
     * Possible Outputs - ORDER_OPENED(include ORDER_NEW and ORDER_PARTIAL), ORDER_NEW, ORDER_PARTIAL, ORDER_FINISHED, ORDER_CANCEL, ORDER_CUTOFF)
     */
    @SerializedName("status")
    var status : String? = null

    /**
     * Amount of toSell tokens from [LooprOrder] dealt
     * Example output - "0x1a055690d9db80000"
     */
    var dealtAmountToSell: BigInteger?
        get() {
            return mDealtAmountToSell?.let { BigInteger(it, 16) }
        }
        set(value) {
            mDealtAmountToSell = value?.toString(16)
        }

    private var mDealtAmountToSell : String? = null

    /**
     * Amount of toBuy tokens from [LooprOrder] dealt
     * Example output - "0x1a055690d9db80000"
     */
    var dealtAmountToBuy: BigInteger?
        get() {
            return mDealtAmountToBuy?.let { BigInteger(it, 16) }
        }
        set(value) {
            mDealtAmountToBuy = value?.toString(16)
        }

    private var mDealtAmountToBuy : String? = null

    /**
     * Amount of toSell tokens from [LooprOrder] cancelled
     * Example output - "0x1a055690d9db80000"
     */
    var cancelledAmountToSell: BigInteger?
        get() {
            return mCancelledAmountToSell?.let { BigInteger(it, 16) }
        }
        set(value) {
            mCancelledAmountToSell = value?.toString(16)
        }

    private var mCancelledAmountToSell : String? = null

    /**
     * Amount of toBuy tokens from [LooprOrder] cancelled
     * Example output - "0x1a055690d9db80000"
     */
    var cancelledAmountToBuy: BigInteger?
        get() {
            return mCancelledAmountToBuy?.let { BigInteger(it, 16) }
        }
        set(value) {
            mCancelledAmountToBuy = value?.toString(16)
        }

    private var mCancelledAmountToBuy : String? = null

    /**
     * Custom class deserializer
     */
    class LooprOrderItemDeserializer : JsonDeserializer<LooprOrderItem> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprOrderItem? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val order = LooprOrderItem()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("originalOrder")?.let {
                    order.originalOrder = context.deserialize(it.asJsonObject, LooprOrder::class.java)
                }
                //}

                jsonObj.get("status")?.let {
                    order.status  = it.asString
                }

                jsonObj.get("dealtAmountB")?.let {
                    order.mDealtAmountToBuy  = it.asString.substring(2)
                }

                jsonObj.get("dealtAmountS")?.let {
                    order.mDealtAmountToSell  = it.asString.substring(2)
                }

                jsonObj.get("cancelledAmountB")?.let {
                    order.mCancelledAmountToBuy  = it.asString.substring(2)
                }

                jsonObj.get("cancelledAmountS")?.let {
                    order.mCancelledAmountToSell  = it.asString.substring(2)
                }

                return order
            }
        }

    }

}