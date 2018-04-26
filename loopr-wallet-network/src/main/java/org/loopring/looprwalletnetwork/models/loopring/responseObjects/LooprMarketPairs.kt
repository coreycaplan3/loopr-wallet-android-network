package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class LooprMarketPairs : RealmObject() {

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
     * The array of all supported markets
     * Example output -  ["SAN-WETH","GNO-WETH","RLC-WETH","AST-WETH"]
     */
    @SerializedName("result")
    var pairs : RealmList<String>? = null
}