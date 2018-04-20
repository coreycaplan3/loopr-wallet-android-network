package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigInteger

open class LooprPortfolio : RealmObject() {

    /**
     * Token symbol
     * Example output - "LRC"
     */
    @SerializedName("token")
    var token: String? = null

    /**
     * Amount of the [token] owned
     * Example output - "0x000001234d"
     */
    var amount: BigInteger?
        get() {
            return mAmount?.let { BigInteger(it) }
        }
        set(value) {
            mAmount = value.toString()
        }

    private var mAmount: String? = null

    /**
     * Percentage of portfolio
     * Example output - "2.35"
     */
    @SerializedName("percentage")
    var percentage: String? = null
}