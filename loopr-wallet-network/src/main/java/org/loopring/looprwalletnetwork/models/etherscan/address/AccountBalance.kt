package org.loopring.looprwalletnetwork.models.etherscan.address

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigDecimal

/**
 * Etherscan API
 *
 * Account balance:
 * ```
 * {
 *   account
 *   balance
 * }
 * ```
 *
 * @author arknw229
 */
open class AccountBalance : RealmObject() {

    /**
     * The address of the account whose balance was retrieved
     */
    @SerializedName("account")
    var address: String? = null

    /**
     * The balance of the account
     */
    var balance: BigDecimal?
        get() {
            return mBalance?.let { BigDecimal(it) }
        }
        set(value) {
            mBalance = value?.toPlainString()
        }

    @SerializedName("balance")
    private var mBalance: String? = null

}