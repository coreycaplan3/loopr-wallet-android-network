package com.loopring.looprwalletnetwork.models.ethplorer.address

/**
 * Ethplorer API
 *
 * Get last address operations
 *
 * token:   show only specified token address operations
 * type:    show operations of specified type only
 * limit:   maximum number of operations `[1 - 10, default = 10]`
 *
 * ```
 * operations: [
 *   {
 *      timestamp:       # operation timestamp
 *      transactionHash: # transaction hash
 *      tokenInfo:       # token data (same format as token info),
 *      type:            # operation type (transfer, approve, issuance, mint, burn, etc),
 *      address:         # operation target address (if one),
 *      from:            # source address (if two addresses involved),
 *      to:              # destination address (if two addresses involved),
 *      value:           # operation value (as is, not reduced to a floating point value),
 *   },
 *   ...
 * ]
 * ```
 *
 * @author arknw229
 */
class EthAddressHistory(
        /**
         * List of [EthTransactionOperationInfo] objects describing the operations in the addresses history
         */
        var operations: MutableList<EthTransactionOperationInfo>? = null
)
