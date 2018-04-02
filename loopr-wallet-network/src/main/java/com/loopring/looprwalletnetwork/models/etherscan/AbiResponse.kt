package com.loopring.looprwalletnetwork.models.etherscan

/**
 * Created by arknw229 on 3/13/18.
 *
 * Etherscan API
 *
 * Retrieve ABI for the given address
 *
 * ```
 * {
 *   status
 *   message
 *   result
 * }
 * ```
 *
 * @author arknw229
 */
class AbiResponse(
        /**
         * Status of the request
         */
        val status: Int? = null,

        /**
         * Status message
         */
        val message: String? = null,

        /**
         * ABI
         */
        val abi: String? = null

)
