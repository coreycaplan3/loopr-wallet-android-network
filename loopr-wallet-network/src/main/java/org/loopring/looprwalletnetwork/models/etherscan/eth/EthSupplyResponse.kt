package org.loopring.looprwalletnetwork.models.etherscan.eth

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/1/18.
 *
 * Etherscan API
 *
 * Retrieves the supply of eth
 *
 * @author arknw229
 */
open class EthSupplyResponse : RealmObject() {

    /**
     * Status of the request. Can be 1 for complete or 0 for failure.
     */
    var status: Int? = null

    /**
     * Status message. Can be "OK" for successful calls or "NOTOK" for failures
     */
    var message: String? = null

    /**
     * The supply of eth
     */
    var supply: BigDecimal?
        get() = mSupply?.let { BigDecimal(it) }
        set(value) {
            mSupply = value?.toPlainString()
        }

    @SerializedName("result")
    private var mSupply: String? = null

}