package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger

open class LooprOrderItem : RealmObject() {

    /**
     * The original [LooprOrder] order info when submitting
     */
    @SerializedName("orginalOrder")
    var id : LooprOrder?  = null

    /**
     * The current order status
     * //TODO - find all potential outputs
     * Example output - "ORDER_CANCEL"
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


}