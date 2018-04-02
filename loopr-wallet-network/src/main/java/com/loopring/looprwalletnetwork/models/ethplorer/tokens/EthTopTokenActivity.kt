package com.loopring.looprwalletnetwork.models.ethplorer.tokens

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Gets data on the activity of the top ERC20 tokens
 *
 * @author arknw229
 */
class EthTopTokenActivity (
    /**
     * A list of [EthTokenInfo] objects each with data on a token
     */
    var tokens: Array<EthTokenInfo>? = null
)
