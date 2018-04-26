package org.loopring.looprwalletnetwork.models.loopring.requestObjects

data class LooprRequestCutoff(
        val address: String,
        val delegateAddress: String,
        val blockNumber: String
) {
}