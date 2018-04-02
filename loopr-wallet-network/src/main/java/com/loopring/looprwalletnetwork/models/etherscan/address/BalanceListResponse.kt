package com.loopring.looprwalletnetwork.models.etherscan.address

import com.google.gson.annotations.SerializedName

/**
 * Created by arknw229 on 2/28/18.
 *
 * Etherscan API
 * Retrieve account balances
 *
 * {
 *   status
 *   message
 *   result {
 *     account
 *     balance
 *   }
 * }
 *
 * @author arknw229
 */
class BalanceListResponse(
        /**
         * Status of the request
         */
        val status: Int = 0,

        /**
         * Status message
         */
        val message: String? = null,

        /**
         * List of account balances
         */
        @SerializedName("result")
        val balances: List<AccountBalance>? = null
) {

    /**
     * Account balance
     *
     * {
     *   account
     *   balance
     * }
     */
    class AccountBalance {
        /**
         * Account ID
         */
        val account: String? = null

        /**
         * balance of the account
         */
        val balance: String? = null
    }

}
