package org.loopring.looprwalletnetwork.models.loopring

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class LooprTokenPriceQuote : RealmObject() {

    /**
     * The token the [price] data is for
     * Example output - "ETH"
     */
    @SerializedName("token")
    var token : String? = null

    /**
     * Price for the [token] in the currency stated in the [LooprPriceQuote] object
     * Example output - 31022.12
     */
    @SerializedName("price")
    var price : String? = null

}