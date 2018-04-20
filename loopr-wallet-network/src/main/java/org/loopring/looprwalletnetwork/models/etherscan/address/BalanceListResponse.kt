package org.loopring.looprwalletnetwork.models.etherscan.address

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 2/28/18.
 *
 * Etherscan API
 *
 * Retrieve account balances:
 *
 * ```
 * {
 *   status
 *   message
 *   result {
 *     account
 *     balance
 *   }
 * }
 *```
 *
 * @author arknw229
 */
open class BalanceListResponse(
        /**
         * Status of the request. Can be 1 for complete or 0 for failure.
         */
        var status: Int = 0,

        /**
         * Status message. Can be "OK" for successful calls or "NOTOK" for failures
         */
        var message: String? = null,

        /**
         * List of account balances in [AccountBalance] objects
         */
        @SerializedName("result")
        var balanceList: RealmList<AccountBalance>? = null
) : RealmObject()
