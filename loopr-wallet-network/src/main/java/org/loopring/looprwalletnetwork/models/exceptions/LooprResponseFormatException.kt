package org.loopring.looprwalletnetwork.models.exceptions

class LooprResponseFormatException(
            override var message: String
    ) : Exception(message) {
}