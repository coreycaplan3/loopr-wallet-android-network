package org.loopring.looprwalletnetwork.models.exceptions

class LooprNetworkException(
        override var message: String,
        var errorCode: Int
) : Exception(message) {
}