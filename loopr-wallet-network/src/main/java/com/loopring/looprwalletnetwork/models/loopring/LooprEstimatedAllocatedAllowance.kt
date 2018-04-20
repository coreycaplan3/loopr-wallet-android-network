package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class LooprEstimatedAllocatedAllowance : RealmObject() {

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
     * The frozen amount in hex format
     * Example output - ["0x2347ad6c"]
     */
    @SerializedName("result")
    var frozenAmounts : RealmList<String>? = null //TODO - check if you can deserialize straight to RealmList
}