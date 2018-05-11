package org.loopring.looprwalletnetwork.models.loopring.requestObjects

data class LooprRequestCutoff(
        val address: String,
        val delegateAddress: String,
        val blockNumber: String
) {
    companion object {
        val BLOCK_EARLIEST = "earliest"
        val BLOCK_LATEST = "latest"
        val BLOCK_PENDING = "pending"
    }
}