package org.loopring.looprwalletnetwork.models.loopring.requestObjects

data class LooprRequestRingMined(
        val ringHash: String,
        val delegateAddress: String,
        val pageIndex: Int,
        val pageSize: Int
) {
}