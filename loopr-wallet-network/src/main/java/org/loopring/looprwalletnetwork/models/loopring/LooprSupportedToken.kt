package org.loopring.looprwalletnetwork.models.loopring

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class LooprSupportedToken : RealmObject() {

    //TODO - expand documentation for each of these

    /**
     * Protocol
     * Example output - "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07"
     */
    @SerializedName("protocol")
    var protocol : String? = null

    /**
     * Symbol
     * Example output - "OMG"
     */
    @SerializedName("symbol")
    var symbol : String? = null

    /**
     * Source
     * Example output - "omisego"
     */
    @SerializedName("source")
    var source : String? = null

    /**
     * Source
     * Example output - false
     */
    @SerializedName("deny")
    var deny : Boolean? = null

    /**
     * Number of decimals the token has
     * Example output - 18
     */
    @SerializedName("decimals")
    var decimals : Int? = null

    /**
     * isMarket
     * Example output - false
     */
    @SerializedName("isMarket")
    var isMarket : Boolean? = null

}