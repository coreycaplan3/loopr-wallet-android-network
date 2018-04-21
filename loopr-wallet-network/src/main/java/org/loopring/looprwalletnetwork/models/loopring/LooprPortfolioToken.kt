package org.loopring.looprwalletnetwork.models.loopring

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigInteger

open class LooprPortfolioToken : RealmObject() {

    /**
     *
     * Example output - "LRC"
     */
    @SerializedName("token")
    var token : String? = null

    /**
     *
     * Example output - "0x000001234d"
     */
    var amount: BigInteger?
        get() {
            return mAmount?.let { BigInteger(mAmount) }
        }
        set(value) {
            mAmount = value.toString()
        }

    private var mAmount : String? = null

    /**
     *
     * Example output - "2.35"
     */
    @SerializedName("percentage")
    var percentage : String? = null
}