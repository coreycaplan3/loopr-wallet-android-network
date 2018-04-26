package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class LooprUnlockResponse : RealmObject() {

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
     * Success or fail info
     * Example output - ["unlock_notice_success"]
     * TODO - if this always returns a single string, deserialize it to string
     */
    @SerializedName("result")
    var pairs : RealmList<String>? = null
}