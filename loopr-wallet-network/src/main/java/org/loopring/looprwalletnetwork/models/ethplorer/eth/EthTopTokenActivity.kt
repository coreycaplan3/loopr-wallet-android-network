package org.loopring.looprwalletnetwork.models.ethplorer.eth

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Gets data on the activity of the top ERC20 tokens
 *
 * @author arknw229
 */
open class EthTopTokenActivity(

        /**
         * A list of [EthTokenInfo] objects each with data on a token
         */
        var tokens: RealmList<EthTokenInfo>? = null
) : RealmObject()
