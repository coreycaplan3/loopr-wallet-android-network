package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import org.loopring.looprwalletnetwork.models.exceptions.LooprNetworkException
import org.loopring.looprwalletnetwork.models.exceptions.LooprResponseFormatException

interface LooprResponse {

    /**
     * TODO - figure out what this id is
     * Example output - 64
     */
    var id : Int?

    /**
     * String representing the version of jsonrpc. Should match the one used in the request
     * Example output - "2.0"
     */
    var jsonrpc : String?

    /**
     * A setter for the id and jsonRpc, which are reused in all loopr response objects
     * Will throw [LooprResponseFormatException] if it's not in the expected format, a sign the rest
     * of the response will also be improperly formatted
     */
    fun setIdJsonRPC(jsonObj: JsonObject) {
        jsonObj.get("id")?.let {
            if (it.asString.toIntOrNull() == null){
                throw LooprResponseFormatException("Response from relay did not contain valid id")
            }
            id = it.asString.toInt()
        }

        jsonObj.get("jsonrpc")?.let {
            jsonrpc  = it.asString
        }
    }

    companion object {
        /**
         * Checks for an error object in the response, in which case something went wrong on the relay
         */
        fun checkForError(jsonObj: JsonObject) {
            jsonObj.get("error")?.let {
                throw (LooprNetworkException(it.asJsonObject.get("code").asString, it.asJsonObject.get("message").asInt))
            }
            return
        }
    }

}