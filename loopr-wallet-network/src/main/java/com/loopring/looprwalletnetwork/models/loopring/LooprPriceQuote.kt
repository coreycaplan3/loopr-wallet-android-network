package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

class LooprPriceQuote : RealmObject() {

    /**
     * TODO - figure out what this id is
     * Example output - 64
     */
    @SerializedName("id")
    var id : Int?  = null

    /**
     * String representing the version of jsonrpc. Should match the one used in the request
     * Example output - "2.0"
     */
    @SerializedName("jsonrpc")
    var jsonrpc : String? = null

    /**
     * The base currency, CNY or USD
     * Example output - "CNY"
     */
    @SerializedName("currency")
    var currency : String? = null

    /**
     * Every token price int the currency
     */
    var tokens : RealmList<LooprTokenPriceQuote>? = null

}