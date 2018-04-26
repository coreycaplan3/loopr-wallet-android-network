package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class LooprSupportedTokenList : RealmObject() {

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
     * A list of [LooprSupportedToken] objects with token support data
     */
    @SerializedName("result")
    var tokens : RealmList<LooprSupportedToken>? = null

}