package org.loopring.looprwalletnetwork.models.etherscan

import io.realm.RealmObject

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
open class AbiResponse(

        /**
         * Status of the request. Can be 1 for complete or 0 for failure.
         */
        var status: Int? = null,

        /**
         * Status message. Can be "OK" for successful calls or "NOTOK" for failures
         */
        var message: String? = null,

        /**
         * The contracts ABI
         */
        var abi: String? = null

) : RealmObject()