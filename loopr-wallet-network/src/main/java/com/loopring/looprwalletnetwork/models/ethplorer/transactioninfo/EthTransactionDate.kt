package com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo

import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * The year month and day of a transaction
 *
 * @author arknw229
 */
open class EthTransactionDate(
        /**
         * Year the transaction occurred in. IE 2018
         */
        var year: Int? = null,

        /**
         * Month the transaction occurred in, form 1-12
         */
        var month: Int? = null,

        /**
         * Day the transaction occurred in, starting from 0 to 30.
         */
        var day: Int? = null
) : RealmObject()